package com.gugu.dts.course.core.inf.pojo

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Course() {
    @Id
    var id: Long = 0
    var name: String? = null
}