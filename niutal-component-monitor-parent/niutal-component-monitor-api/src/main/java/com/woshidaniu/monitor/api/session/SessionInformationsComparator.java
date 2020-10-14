package com.woshidaniu.monitor.api.session;

import java.io.Serializable;
import java.util.Comparator;

public class SessionInformationsComparator implements
		Comparator<SessionInformations>, Serializable {
	private static final long serialVersionUID = 1L;

	/** {@inheritDoc} */
	@Override
	public int compare(SessionInformations session1,
			SessionInformations session2) {
		if (session1.getLastAccess().before(session2.getLastAccess())) {
			return 1;
		} else if (session1.getLastAccess().after(session2.getLastAccess())) {
			return -1;
		} else {
			return 0;
		}
	}
}