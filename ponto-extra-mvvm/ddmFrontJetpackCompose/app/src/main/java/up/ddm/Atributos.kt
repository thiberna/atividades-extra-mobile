package up.ddm

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "atributos")
data class Atributos(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var forca: Int = 8,
    var destreza: Int = 8,
    var constituicao: Int = 8,
    var inteligencia: Int = 8,
    var sabedoria: Int = 8,
    var carisma: Int = 8
) {
    fun getPontosDisponiveis(): Int {
        val totalPontos = forca + destreza + constituicao + inteligencia + sabedoria + carisma
        return 27 - totalPontos
    }

    fun setAtributo(valor: Int) {
        if (valor < 8 || valor > 15) {
            throw IllegalArgumentException("O valor do atributo deve estar entre 8 e 15.")
        }
    }
}
