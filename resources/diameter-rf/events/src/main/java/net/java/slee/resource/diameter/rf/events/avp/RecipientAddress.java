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
package net.java.slee.resource.diameter.rf.events.avp;

import net.java.slee.resource.diameter.base.events.avp.GroupedAvp;

/**
 * Defines an interface representing the Recipient-Address grouped AVP type.<br>
 * <br>
 * From the Diameter Rf Reference Point Protocol Details (3GPP TS 32.299 V7.1.0) specification:
 * <pre>
 * 7.2.91 Recipient-Address AVP
 *  The Recipient-Address AVP (AVP code 1201) is of type Grouped. 
 *  Its purpose is to identify the recipient of a MM. 
 *  
 *  It has the following ABNF grammar: 
 *  Recipient-Address ::= AVP Header: 1201 
 *      [ Address-Type ] 
 *      [ Address-Data ] 
 *      [ Address-Domain ] 
 *      [ Addressee-Type ]
 * </pre>
 * 
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface RecipientAddress extends GroupedAvp {

  /**
   * Returns the value of the Address-Data AVP, of type UTF8String. A return value of null implies that the AVP has not been set.
   */
  abstract String getAddressData();

  /**
   * Returns the value of the Address-Domain AVP, of type Grouped. A return value of null implies that the AVP has not been set.
   */
  abstract AddressDomain getAddressDomain();

  /**
   * Returns the value of the Addressee-Type AVP, of type Enumerated. A return value of null implies that the AVP has not been set.
   */
  abstract AddresseeType getAddresseeType();

  /**
   * Returns the value of the Address-Type AVP, of type Enumerated. A return value of null implies that the AVP has not been set.
   */
  abstract AddressType getAddressType();

  /**
   * Returns true if the Address-Data AVP is present in the message.
   */
  abstract boolean hasAddressData();

  /**
   * Returns true if the Address-Domain AVP is present in the message.
   */
  abstract boolean hasAddressDomain();

  /**
   * Returns true if the Addressee-Type AVP is present in the message.
   */
  abstract boolean hasAddresseeType();

  /**
   * Returns true if the Address-Type AVP is present in the message.
   */
  abstract boolean hasAddressType();

  /**
   * Sets the value of the Address-Data AVP, of type UTF8String.
   */
  abstract void setAddressData(String addressData);

  /**
   * Sets the value of the Address-Domain AVP, of type Grouped.
   */
  abstract void setAddressDomain(AddressDomain addressDomain);

  /**
   * Sets the value of the Addressee-Type AVP, of type Enumerated.
   */
  abstract void setAddresseeType(AddresseeType addresseeType);

  /**
   * Sets the value of the Address-Type AVP, of type Enumerated.
   */
  abstract void setAddressType(AddressType addressType);

}
