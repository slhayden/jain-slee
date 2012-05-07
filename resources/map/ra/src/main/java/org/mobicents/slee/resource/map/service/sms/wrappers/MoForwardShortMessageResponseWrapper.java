package org.mobicents.slee.resource.map.service.sms.wrappers;

import org.mobicents.protocols.ss7.map.api.primitives.MAPExtensionContainer;
import org.mobicents.protocols.ss7.map.api.service.sms.MoForwardShortMessageResponse;
import org.mobicents.protocols.ss7.map.api.service.sms.SmsSignalInfo;

/**
 * 
 * @author amit bhayani
 *
 */
public class MoForwardShortMessageResponseWrapper extends SmsMessageWrapper<MoForwardShortMessageResponse> implements
		MoForwardShortMessageResponse {

	private static final String EVENT_TYPE_NAME = "ss7.map.service.sms.MO_FORWARD_SHORT_MESSAGE_RESPONSE";

	public MoForwardShortMessageResponseWrapper(MAPDialogSmsWrapper mAPDialog, MoForwardShortMessageResponse req) {
		super(mAPDialog, EVENT_TYPE_NAME, req);
	}

	@Override
	public MAPExtensionContainer getExtensionContainer() {
		return this.wrappedEvent.getExtensionContainer();
	}

	@Override
	public SmsSignalInfo getSM_RP_UI() {
		return this.wrappedEvent.getSM_RP_UI();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MoForwardShortMessageResponse [wrapped=" + this.wrappedEvent + "]";
	}
}
