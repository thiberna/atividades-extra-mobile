package up.ddm

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "personagem",
    foreignKeys = [ForeignKey(
        entity = Atributos::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("atributosId"),
        onDelete = ForeignKey.CASCADE
    )]
)
class Personagem {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var nome: String = ""
    var atributosId: Int = 0
}
