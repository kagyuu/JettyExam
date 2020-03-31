package com.example.jetty.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Application Binary Entity.
 * @author atsushi.hondoh
 */
@Data
@Entity
@NamedQueries({
    @NamedQuery(name = "AppBinaryEntity.maxBranchNo", query = "SELECT max(o.branchNo) FROM AppBinaryEntity o WHERE o.name = :name AND o.version = :version"),
    @NamedQuery(name = "AppBinaryEntity.findByName", query = "SELECT o FROM AppBinaryEntity o WHERE o.name = :name AND o.enabled = true ORDER BY o.id DESC"),
    @NamedQuery(name = "AppBinaryEntity.findAll", query = "SELECT o FROM AppBinaryEntity o ORDER BY o.id DESC"),
    @NamedQuery(name = "AppBinaryEntity.names", query = "SELECT o.name FROM AppBinaryEntity o GROUP BY o.name")
})
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

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "app", fetch = FetchType.LAZY)
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
        //return "com.example.jetty.entity.AppBinaryEntity[ id=" + id + " ]";
        return ToStringBuilder.reflectionToString(this);
    }
}
