package com.woshidaniu.export.service.utils.dbf;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DBFReader {

	private DataInputStream stream;
	private JDBField fields[];
	private byte nextRecord[];
	private int nFieldCount;

	public DBFReader(String s) throws JDBFException {
		stream = null;
		fields = null;
		nextRecord = null;
		nFieldCount = 0;
		try {
			init(new FileInputStream(s));
		} catch (FileNotFoundException filenotfoundexception) {
			throw new JDBFException(filenotfoundexception);
		}
	}

	public DBFReader(InputStream inputstream) throws JDBFException {
		stream = null;
		fields = null;
		nextRecord = null;
		init(inputstream);
	}

	private void init(InputStream inputstream) throws JDBFException {
		try {
			stream = new DataInputStream(inputstream);
			int i = readHeader();
			fields = new JDBField[i];
			int j = 1;
			for (int k = 0; k < i; k++) {
				fields[k] = readFieldHeader();
				if (fields[k] != null) {
					nFieldCount++;
					j += fields[k].getLength();
				}
			}

			nextRecord = new byte[j];
			try {
				stream.readFully(nextRecord);
			} catch (EOFException eofexception) {
				nextRecord = null;
				stream.close();
			}
			int l = 0;
			for (int i1 = 0; i1 < j; i1++) {
				if (nextRecord == null)
					break;
				else {
					if (nextRecord[i1] != 32 && nextRecord[i1] != 42)
						continue;
					l = i1;
					break;
				}

			}

			if (l > 0) {
				byte abyte0[] = new byte[l];
				stream.readFully(abyte0);
				for (int j1 = 0; j1 < j - l; j1++)
					nextRecord[j1] = nextRecord[j1 + l];

				for (int k1 = 0; k1 < l; k1++)
					nextRecord[j - k1 - 1] = abyte0[l - k1 - 1];

			}
		} catch (IOException ioexception) {
			throw new JDBFException(ioexception);
		}
	}

	private int readHeader() throws IOException, JDBFException {
		byte abyte0[] = new byte[16];
		try {
			stream.readFully(abyte0);
		} catch (EOFException eofexception) {
			throw new JDBFException("Unexpected end of file reached.");
		}
		int i = abyte0[8];
		if (i < 0)
			i += 256;
		i += 256 * abyte0[9];
		i = --i / 32;
		i--;
		try {
			stream.readFully(abyte0);
		} catch (EOFException eofexception1) {
			throw new JDBFException("Unexpected end of file reached.");
		}
		return i;
	}

	private JDBField readFieldHeader() throws IOException, JDBFException {
		byte abyte0[] = new byte[16];
		try {
			stream.readFully(abyte0);
		} catch (EOFException eofexception) {
			throw new JDBFException("Unexpected end of file reached.");
		}
		if (abyte0[0] == 13 || abyte0[0] == 0) {
			stream.readFully(abyte0);
			return null;
		}
		StringBuffer stringbuffer = new StringBuffer(10);
		int i = 0;
		for (i = 0; i < 10; i++)
			if (abyte0[i] == 0)
				break;

		stringbuffer.append(new String(abyte0, 0, i));
		char c = (char) abyte0[11];
		try {
			stream.readFully(abyte0);
		} catch (EOFException eofexception1) {
			throw new JDBFException("Unexpected end of file reached.");
		}
		int j = abyte0[0];
		int k = abyte0[1];
		if (j < 0)
			j += 256;
		if (k < 0)
			k += 256;
		return new JDBField(stringbuffer.toString(), c, j, k);
	}

	public Object[] nextRecord() throws JDBFException {
		if (!hasNextRecord())
			throw new JDBFException("No more records available.");
		Object aobj[] = new Object[nFieldCount];
		int i = 1;
		for (int j = 0; j < aobj.length; j++) {
			int k = fields[j].getLength();
			StringBuffer stringbuffer = new StringBuffer(k);
			stringbuffer.append(new String(nextRecord, i, k));
			aobj[j] = fields[j].parse(stringbuffer.toString());
			i += fields[j].getLength();
		}

		try {
			stream.readFully(nextRecord);
		} catch (EOFException eofexception) {
			nextRecord = null;
		} catch (IOException ioexception) {
			throw new JDBFException(ioexception);
		}
		return aobj;
	}

	public String[] nextRecordString() throws JDBFException {
		if (!hasNextRecord())
			throw new JDBFException("No more records available.");
		String as[] = new String[nFieldCount];
		int i = 1;
		for (int j = 0; j < as.length; j++) {
			int k = fields[j].getLength();
			StringBuffer stringbuffer = new StringBuffer(k);
			stringbuffer.append(new String(nextRecord, i, k).trim());
			as[j] = stringbuffer.toString();
			i += fields[j].getLength();
		}

		try {
			stream.readFully(nextRecord);
		} catch (EOFException eofexception) {
			nextRecord = null;
		} catch (IOException ioexception) {
			throw new JDBFException(ioexception);
		}
		return as;
	}
	
	public List<String[]> getDataList() throws Exception{
		List<String[]> list = new ArrayList<String[]>();
		String[] dataStrs = new String[nFieldCount];
		//��ͷ�ֶ�
		for (int b = 0; b < getFieldCount(); b++) {
			dataStrs[b] = getField(b).getName();
		}
		list.add(dataStrs);
		for (int i = 0; hasNextRecord(); i++) {
			String[] aobj = nextRecordString();
			list.add(aobj);
		}
		return list;
	}

	public void close() throws JDBFException {
		nextRecord = null;
		if (stream != null) {
			try {
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public int getFieldCount() {
		return nFieldCount;
	}

	public JDBField getField(int i) {
		return fields[i];
	}

	public boolean hasNextRecord() {
		return nextRecord != null;
	}

}