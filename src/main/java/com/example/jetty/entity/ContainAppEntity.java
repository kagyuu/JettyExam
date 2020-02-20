package com.example.jetty.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import lombok.Data;

/**
 * The connector object between AppBinary and Resource.
 * <pre>
 * AppBinary *--1 ContainAppEntity 1--* Resource
 * </pre>
 * <ul>
 * <li>Many AppBinary share 1 ContainAppEntity.</li>
 * <li>Many Resource share 1 ContainAppEntity.</li>
 * <li>1 Resource relates to 1 AppBinary.</li>
 * <li>1 AppBinary relates to 1 Resource.</li>
 * </ul>
 * @author atsushi.hondoh
 */
@Data
@Entity
public class ContainAppEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Version
    private Timestamp lastupdate = null;
    
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private ResourceEntity resource;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private AppBinaryEntity app;
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContainAppEntity)) {
            return false;
        }
        ContainAppEntity other = (ContainAppEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.jetty.entity.ContainAppEntity[ id=" + id + " ]";
    }
    
}
