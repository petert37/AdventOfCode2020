package day4

import java.io.File

fun main() {
    val input = File("src/day4/input.txt").readLines()

    val passports = mutableListOf<Passport>()

    val fields = mutableMapOf<String, String>()
    for (i in input.indices) {
        val lineFields = if (input[i].isEmpty()) emptyList() else input[i].split(" ")
        lineFields.forEach { lineField ->
            val field = lineField.split(":")
            fields[field[0]] = field[1]
        }
        if (i == input.size - 1 || input[i].isEmpty()) {
            passports.add(
                Passport(
                    birthYear = fields["byr"],
                    issueYear = fields["iyr"],
                    expirationYear = fields["eyr"],
                    height = fields["hgt"],
                    hairColor = fields["hcl"],
                    eyeColor = fields["ecl"],
                    passportId = fields["pid"],
                    countryId = fields["cid"]
                )
            )
            fields.clear()
        }
    }

    println("Has all required fields: ${passports.count { it.hasRequiredFields }}")
    println("Valid: ${passports.count { it.valid }}")

}

data class Passport(
    val birthYear: String? = null,
    val issueYear: String? = null,
    val expirationYear: String? = null,
    val height: String? = null,
    val hairColor: String? = null,
    val eyeColor: String? = null,
    val passportId: String? = null,
    val countryId: String? = null
) {

    val hasRequiredFields = birthYear != null
            && issueYear != null
            && expirationYear != null
            && height != null
            && hairColor != null
            && eyeColor != null
            && passportId != null

    val valid = validYear(birthYear, 1920, 2002)
            && validYear(issueYear, 2010, 2020)
            && validYear(expirationYear, 2020, 2030)
            && validHeight(height)
            && validHairColor(hairColor)
            && validEyeColor(eyeColor)
            && validPassportId(passportId)

    private fun validYear(value: String?, min: Int, max: Int): Boolean {
        if (value == null) return false
        if (value.length != 4) return false
        return value.toInt() in min..max
    }

    private fun validHeight(value: String?): Boolean {
        if (value == null) return false
        if (value.length < 3) return false
        val unit = value.substring(value.length - 2, value.length)
        val number = value.substring(0, value.length - 2).toInt()
        return when (unit) {
            "cm" -> number in 150..193
            "in" -> number in 59..76
            else -> false
        }
    }

    private fun validHairColor(value: String?): Boolean {
        if (value == null) return false
        if (value.length != 7) return false
        if (value[0] != '#') return false
        return value.substring(1, value.length).all {
            it in '0'..'9' || it in 'a'..'f'
        }
    }

    private fun validEyeColor(value: String?): Boolean {
        if (value == null) return false
        return value == "amb"
                || value == "blu"
                || value == "brn"
                || value == "gry"
                || value == "grn"
                || value == "hzl"
                || value == "oth"
    }

    private fun validPassportId(value: String?): Boolean {
        if (value == null) return false
        if (value.length != 9) return false
        return value.all { it in '0'..'9' }
    }

}