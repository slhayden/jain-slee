/*
 * JBoss, Home of Professional Open Source
 * 
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a full listing
 * of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU General Public License, v. 2.0.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License,
 * v. 2.0 along with this distribution; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 */
package org.mobicents.slee.resource.diameter.rf.events.avp;

import java.util.Date;

import net.java.slee.resource.diameter.rf.events.avp.TimeStamps;

import org.mobicents.slee.resource.diameter.base.events.avp.GroupedAvpImpl;

/**
 * TimeStampsImpl.java
 *
 * <br>Project:  mobicents
 * <br>1:10:40 AM Apr 12, 2009 
 * <br>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class TimeStampsImpl extends GroupedAvpImpl implements TimeStamps {

  public TimeStampsImpl() {
    super();
  }

  /**
   * @param code
   * @param vendorId
   * @param mnd
   * @param prt
   * @param value
   */
  public TimeStampsImpl( int code, long vendorId, int mnd, int prt, byte[] value ) {
    super( code, vendorId, mnd, prt, value );
  }

  /* (non-Javadoc)
   * @see net.java.slee.resource.diameter.rf.events.avp.TimeStamps#getSipRequestTimestamp()
   */
  public Date getSipRequestTimestamp() {
    return getAvpAsTime(DiameterRfAvpCodes.SIP_REQUEST_TIMESTAMP, DiameterRfAvpCodes.TGPP_VENDOR_ID);
  }

  /* (non-Javadoc)
   * @see net.java.slee.resource.diameter.rf.events.avp.TimeStamps#getSipResponseTimestamp()
   */
  public Date getSipResponseTimestamp() {
    return getAvpAsTime(DiameterRfAvpCodes.SIP_RESPONSE_TIMESTAMP, DiameterRfAvpCodes.TGPP_VENDOR_ID);
  }

  /* (non-Javadoc)
   * @see net.java.slee.resource.diameter.rf.events.avp.TimeStamps#hasSipRequestTimestamp()
   */
  public boolean hasSipRequestTimestamp() {
    return hasAvp( DiameterRfAvpCodes.SIP_REQUEST_TIMESTAMP, DiameterRfAvpCodes.TGPP_VENDOR_ID );
  }

  /* (non-Javadoc)
   * @see net.java.slee.resource.diameter.rf.events.avp.TimeStamps#hasSipResponseTimestamp()
   */
  public boolean hasSipResponseTimestamp() {
    return hasAvp( DiameterRfAvpCodes.SIP_RESPONSE_TIMESTAMP, DiameterRfAvpCodes.TGPP_VENDOR_ID );
  }

  /* (non-Javadoc)
   * @see net.java.slee.resource.diameter.rf.events.avp.TimeStamps#setSipRequestTimestamp(java.util.Date)
   */
  public void setSipRequestTimestamp( Date sipRequestTimestamp ) {
    addAvp(DiameterRfAvpCodes.SIP_REQUEST_TIMESTAMP, DiameterRfAvpCodes.TGPP_VENDOR_ID, sipRequestTimestamp);
  }

  /* (non-Javadoc)
   * @see net.java.slee.resource.diameter.rf.events.avp.TimeStamps#setSipResponseTimestamp(java.util.Date)
   */
  public void setSipResponseTimestamp( Date sipResponseTimestamp ) {
    addAvp(DiameterRfAvpCodes.SIP_RESPONSE_TIMESTAMP, DiameterRfAvpCodes.TGPP_VENDOR_ID, sipResponseTimestamp);
  }

}
