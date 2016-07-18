//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.07.18 at 03:13:06 PM PDT 
//


package net.systemsbiology.regis_web.pepxml;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://regis-web.systemsbiology.net/pepXML}asapratio_lc_lightpeak"/>
 *         &lt;element ref="{http://regis-web.systemsbiology.net/pepXML}asapratio_lc_heavypeak"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ratio" use="required" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *       &lt;attribute name="error" use="required" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *       &lt;attribute name="charge" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="use" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "asapratioLcLightpeak",
    "asapratioLcHeavypeak"
})
@XmlRootElement(name = "asapratio_contribution")
public class AsapratioContribution {

    @XmlElement(name = "asapratio_lc_lightpeak", required = true)
    protected AsapratioLcLightpeak asapratioLcLightpeak;
    @XmlElement(name = "asapratio_lc_heavypeak", required = true)
    protected AsapratioLcHeavypeak asapratioLcHeavypeak;
    @XmlAttribute(name = "ratio", required = true)
    protected BigDecimal ratio;
    @XmlAttribute(name = "error", required = true)
    protected BigDecimal error;
    @XmlAttribute(name = "charge", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger charge;
    @XmlAttribute(name = "use", required = true)
    @XmlSchemaType(name = "unsignedByte")
    protected short use;

    /**
     * Gets the value of the asapratioLcLightpeak property.
     * 
     * @return
     *     possible object is
     *     {@link AsapratioLcLightpeak }
     *     
     */
    public AsapratioLcLightpeak getAsapratioLcLightpeak() {
        return asapratioLcLightpeak;
    }

    /**
     * Sets the value of the asapratioLcLightpeak property.
     * 
     * @param value
     *     allowed object is
     *     {@link AsapratioLcLightpeak }
     *     
     */
    public void setAsapratioLcLightpeak(AsapratioLcLightpeak value) {
        this.asapratioLcLightpeak = value;
    }

    /**
     * Gets the value of the asapratioLcHeavypeak property.
     * 
     * @return
     *     possible object is
     *     {@link AsapratioLcHeavypeak }
     *     
     */
    public AsapratioLcHeavypeak getAsapratioLcHeavypeak() {
        return asapratioLcHeavypeak;
    }

    /**
     * Sets the value of the asapratioLcHeavypeak property.
     * 
     * @param value
     *     allowed object is
     *     {@link AsapratioLcHeavypeak }
     *     
     */
    public void setAsapratioLcHeavypeak(AsapratioLcHeavypeak value) {
        this.asapratioLcHeavypeak = value;
    }

    /**
     * Gets the value of the ratio property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRatio() {
        return ratio;
    }

    /**
     * Sets the value of the ratio property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRatio(BigDecimal value) {
        this.ratio = value;
    }

    /**
     * Gets the value of the error property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getError() {
        return error;
    }

    /**
     * Sets the value of the error property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setError(BigDecimal value) {
        this.error = value;
    }

    /**
     * Gets the value of the charge property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCharge() {
        return charge;
    }

    /**
     * Sets the value of the charge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCharge(BigInteger value) {
        this.charge = value;
    }

    /**
     * Gets the value of the use property.
     * 
     */
    public short getUse() {
        return use;
    }

    /**
     * Sets the value of the use property.
     * 
     */
    public void setUse(short value) {
        this.use = value;
    }

}
