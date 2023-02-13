package br.com.edsontofolo.interfaceadapters.database.entities

import jakarta.persistence.*

@Entity
@Table(name = "people")
data class PersonEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = "first_name", nullable = false, length = 10)
    var firstName: String = "",
    @Column(name = "last_name", nullable = false, length = 10)
    var lastName: String = "",
    @Column(name = "address", nullable = false, length = 30)
    var address: String = "",
    @Column(name = "gender", nullable = false, length = 6)
    var gender: String = ""
)