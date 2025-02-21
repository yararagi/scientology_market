//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0.1 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2025.02.21 at 09:25:45 AM UTC 
//


package app.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;



/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="sede" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence minOccurs="0"&gt;
 *                   &lt;element name="periodo" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;attribute name="mese" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="n_tessere_create" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="nome" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="indirizzo" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="n_tot_di_tessere_create" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sede"
})
@XmlRootElement(name = "sedi")
public class PopolaritaSedi {

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Sedi {\n");
        for (Sede s : sede) {
            str.append(s.toString()).append("\n");
        }
        str.append("}");
        return str.toString();
    }


    @XmlElement(required = true)
    protected List<PopolaritaSedi.Sede> sede;

    /**
     * Gets the value of the sede property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sede property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSede().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PopolaritaSedi.Sede }
     * 
     * 
     */
    public List<PopolaritaSedi.Sede> getSede() {
        if (sede == null) {
            sede = new ArrayList<PopolaritaSedi.Sede>();
        }
        return this.sede;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence minOccurs="0"&gt;
     *         &lt;element name="periodo" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attribute name="mese" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="n_tessere_create" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="nome" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="indirizzo" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="n_tot_di_tessere_create" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "periodo"
    })
    public static class Sede {

        @Override
        public String toString() {
            StringBuilder str = new StringBuilder("\t• " + nome + ", " + indirizzo + ", totale tessere create: " + nTotDiTessereCreate + "\n");
            if (periodo != null) {
                str.append("\t[ \n");
                for (Periodo p : periodo) {
                    str.append("\t\t").append(p.getMese()).append(" -> n. tessere create: ").append(p.getNTessereCreate()).append("\n");
                }
                str.append("\t]\n");
            }
            return str.toString();
        }


        protected List<PopolaritaSedi.Sede.Periodo> periodo;
        @XmlAttribute(name = "nome", required = true)
        protected String nome;
        @XmlAttribute(name = "indirizzo", required = true)
        protected String indirizzo;
        @XmlAttribute(name = "n_tot_di_tessere_create", required = true)
        protected BigInteger nTotDiTessereCreate;

        /**
         * Gets the value of the periodo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the periodo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPeriodo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PopolaritaSedi.Sede.Periodo }
         * 
         * 
         */
        public List<PopolaritaSedi.Sede.Periodo> getPeriodo() {
            if (periodo == null) {
                periodo = new ArrayList<PopolaritaSedi.Sede.Periodo>();
            }
            return this.periodo;
        }

        /**
         * Gets the value of the nome property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNome() {
            return nome;
        }

        /**
         * Sets the value of the nome property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNome(String value) {
            this.nome = value;
        }

        /**
         * Gets the value of the indirizzo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIndirizzo() {
            return indirizzo;
        }

        /**
         * Sets the value of the indirizzo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIndirizzo(String value) {
            this.indirizzo = value;
        }

        /**
         * Gets the value of the nTotDiTessereCreate property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getNTotDiTessereCreate() {
            return nTotDiTessereCreate;
        }

        /**
         * Sets the value of the nTotDiTessereCreate property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setNTotDiTessereCreate(BigInteger value) {
            this.nTotDiTessereCreate = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;attribute name="mese" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="n_tessere_create" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Periodo {

            @Override
            public String toString() {
                return new StringBuilder("Periodo [mese=")
                        .append(mese)
                        .append(", nTessereCreate=")
                        .append(nTessereCreate)
                        .append("]")
                        .toString();
            }

            @XmlAttribute(name = "mese", required = true)
            protected String mese;
            @XmlAttribute(name = "n_tessere_create", required = true)
            protected BigInteger nTessereCreate;

            /**
             * Gets the value of the mese property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMese() {
                return mese;
            }

            /**
             * Sets the value of the mese property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMese(String value) {
                this.mese = value;
            }

            /**
             * Gets the value of the nTessereCreate property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getNTessereCreate() {
                return nTessereCreate;
            }

            /**
             * Sets the value of the nTessereCreate property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setNTessereCreate(BigInteger value) {
                this.nTessereCreate = value;
            }

        }

    }

}
