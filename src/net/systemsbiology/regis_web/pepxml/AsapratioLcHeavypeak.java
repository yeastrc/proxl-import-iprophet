//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.07.18 at 03:13:06 PM PDT 
//


package net.systemsbiology.regis_web.pepxml;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
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
 *       &lt;attribute name="status" use="required" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *       &lt;attribute name="left_valley" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="right_valley" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="background" use="required" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *       &lt;attribute name="area" use="required" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *       &lt;attribute name="area_error" use="required" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *       &lt;attribute name="time" use="required" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *       &lt;attribute name="time_width" use="required" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *       &lt;attribute name="is_heavy" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "asapratio_lc_heavypeak")
public class AsapratioLcHeavypeak {

    @XmlAttribute(name = "status", required = true)
    protected byte status;
    @XmlAttribute(name = "left_valley", required = true)
    protected int leftValley;
    @XmlAttribute(name = "right_valley", required = true)
    protected int rightValley;
    @XmlAttribute(name = "background", required = true)
    protected BigDecimal background;
    @XmlAttribute(name = "area", required = true)
    protected BigDecimal area;
    @XmlAttribute(name = "area_error", required = true)
    protected BigDecimal areaError;
    @XmlAttribute(name = "time", required = true)
    protected BigDecimal time;
    @XmlAttribute(name = "time_width", required = true)
    protected BigDecimal timeWidth;
    @XmlAttribute(name = "is_heavy", required = true)
    protected String isHeavy;

    /**
     * Gets the value of the status property.
     * 
     */
    public byte getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     */
    public void setStatus(byte value) {
        this.status = value;
    }

    /**
     * Gets the value of the leftValley property.
     * 
     */
    public int getLeftValley() {
        return leftValley;
    }

    /**
     * Sets the value of the leftValley property.
     * 
     */
    public void setLeftValley(int value) {
        this.leftValley = value;
    }

    /**
     * Gets the value of the rightValley property.
     * 
     */
    public int getRightValley() {
        return rightValley;
    }

    /**
     * Sets the value of the rightValley property.
     * 
     */
    public void setRightValley(int value) {
        this.rightValley = value;
    }

    /**
     * Gets the value of the background property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBackground() {
        return background;
    }

    /**
     * Sets the value of the background property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBackground(BigDecimal value) {
        this.background = value;
    }

    /**
     * Gets the value of the area property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getArea() {
        return area;
    }

    /**
     * Sets the value of the area property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setArea(BigDecimal value) {
        this.area = value;
    }

    /**
     * Gets the value of the areaError property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAreaError() {
        return areaError;
    }

    /**
     * Sets the value of the areaError property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAreaError(BigDecimal value) {
        this.areaError = value;
    }

    /**
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTime(BigDecimal value) {
        this.time = value;
    }

    /**
     * Gets the value of the timeWidth property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTimeWidth() {
        return timeWidth;
    }

    /**
     * Sets the value of the timeWidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTimeWidth(BigDecimal value) {
        this.timeWidth = value;
    }

    /**
     * Gets the value of the isHeavy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsHeavy() {
        return isHeavy;
    }

    /**
     * Sets the value of the isHeavy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsHeavy(String value) {
        this.isHeavy = value;
    }

}
