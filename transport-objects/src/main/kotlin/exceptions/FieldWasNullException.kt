package exceptions

class FieldWasNullException(
    var fieldName:String
): Exception(fieldName) {
}