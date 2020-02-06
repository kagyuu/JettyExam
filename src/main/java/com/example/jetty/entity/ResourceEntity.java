/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.jetty.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import lombok.Data;

/**
 *
 * @author hondou.atsushi
 */
@Data
@Entity
public class ResourceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String version;
    
    private int branchNo;
    
    private String name;
    
    @Column(length=2048)
    private String directory;
    
    private boolean enabled;

    @Version
    private Timestamp lastupdate = null;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resource")
    private List<ContainAppEntity> contains;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResourceEntity)) {
            return false;
        }
        ResourceEntity other = (ResourceEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.jetty.entity.ResourceEntity[ id=" + id + " ]";
    }
    
}
