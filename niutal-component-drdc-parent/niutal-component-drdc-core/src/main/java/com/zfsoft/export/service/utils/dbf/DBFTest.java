package com.woshidaniu.export.service.utils.dbf;

import java.util.ArrayList;
import java.util.List;

public class DBFTest {

	/**
	 * ��DBF�ļ�
	 */
	private static void readDBF() {
		try {
			System.out.println("���ڶ�ȡ�ļ���");

			DBFReader dbfreader = new DBFReader("d:/aa.dbf");
			for (int b = 0; b < dbfreader.getFieldCount(); b++) {
				if (b > 0)
					System.out.print(",");
				System.out.print(dbfreader.getField(b).getName().trim()
						.toUpperCase());

				if (b == (dbfreader.getFieldCount() - 1))
					System.out.print("\n");

			}
			for (int i = 0; dbfreader.hasNextRecord(); i++) {
				String aobj[] = dbfreader.nextRecordString();
				for (int b = 0; b < dbfreader.getFieldCount(); b++) {
					if (b > 0)
						System.out.print(",");
					System.out.print(aobj[b].trim());

					if (b == (dbfreader.getFieldCount() - 1))
						System.out.print("\n");

				}
			}
			System.out.println("��ȡ�ļ��ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * ��DBF�ļ�
	 */
	private static void readDBF2() {
		try {
			System.out.println("���ڶ�ȡ�ļ���");
			List<String[]> list = new ArrayList<String[]>();
			DBFReader dbfreader = new DBFReader("d:/aa.dbf");

			String[] dataStrs = new String[dbfreader.getFieldCount()];
			// ��ͷ�ֶ�
			for (int b = 0; b < dbfreader.getFieldCount(); b++) {
				dataStrs[b] = dbfreader.getField(b).getName();
			}
			list.add(dataStrs);
			for (int i = 0; dbfreader.hasNextRecord(); i++) {
				String[] aobj = dbfreader.nextRecordString();
				list.add(aobj);
			}
			System.out.println("��ȡ�ļ��ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * дDBF�ļ�
	 */
	private static void writeDBF() {

		try {
			JDBField[] ajdbfield = new JDBField[4];
			ajdbfield[0] = new JDBField("�ֶ�1afas  ", 'C', 255, 0);
			ajdbfield[1] = new JDBField("ieldajdbfield���ֶ�", 'C', 255, 0);
			ajdbfield[2] = new JDBField("fidfleadffdad3", 'C', 20, 0);
			ajdbfield[3] = new JDBField("fildfed4", 'C', 20, 0);

			System.out.println("�������DBF�ֶΣ�");
			/*
			 * ��� DBF �ļ�
			 */
			DBFWriter dbfwriter = new DBFWriter("d:/aa.dbf", ajdbfield);
			Object[] aobj = new Object[4];
			aobj[0] = "bfi�ݣ�";
			aobj[1] = "1";
			aobj[2] = "1";
			aobj[3] = "1";

			System.out.println("����д����ݣ�");

			dbfwriter.addRecord(aobj);
			dbfwriter.addRecord(aobj);
			dbfwriter.close();

			System.out.println("ִ�гɹ���");
		} catch (JDBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		writeDBF();
		readDBF2();
	}
}
