package com.example.jetty.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import lombok.Data;

/**
 * Application Binary Entity.
 * @author atsushi.hondoh
 */
@Data
@Entity
public class AppBinaryEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String version;

    private int branchNo;

    private String name;

    private boolean enabled;

    @Version
    private Timestamp lastupdate = null;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "app")
    private List<ContainAppEntity> containedBy;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppBinaryEntity)) {
            return false;
        }
        AppBinaryEntity other = (AppBinaryEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.jetty.entity.AppBinaryEntity[ id=" + id + " ]";
    }

}
