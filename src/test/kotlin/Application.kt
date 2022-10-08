import java.util.UUID

class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            for (i in 1..100) {
                val s = "INSERT INTO `uis_goods` (`id`, `putter_name`, `putter_uid`, `caller_name`, `caller_uid`, `price`, `item_info`, `item_tag`, `description`, `been_sold`, `create_date`, `deal_date`, `meta`) VALUES ('${UUID.randomUUID().toString()}', 'moeKiwiSAMA', '064d47db-af17-3ed5-bd47-ba89fe9a01b6', '', '', 1.00, 'rO0ABXNyABpvcmcuYnVra2l0LnV0aWwuaW8uV3JhcHBlcvJQR+zxEm8FAgABTAADbWFwdAAPTGphdmEvdXRpbC9NYXA7eHBzcgA1Y29tLmdvb2dsZS5jb21tb24uY29sbGVjdC5JbW11dGFibGVNYXAkU2VyaWFsaXplZEZvcm0AAAAAAAAAAAIAAkwABGtleXN0ABJMamF2YS9sYW5nL09iamVjdDtMAAZ2YWx1ZXNxAH4ABHhwdXIAE1tMamF2YS5sYW5nLk9iamVjdDuQzlifEHMpbAIAAHhwAAAABHQAAj09dAABdnQABHR5cGV0AARtZXRhdXEAfgAGAAAABHQAHm9yZy5idWtraXQuaW52ZW50b3J5Lkl0ZW1TdGFja3NyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAwwdAAORU5DSEFOVEVEX0JPT0tzcQB+AABzcQB+AAN1cQB+AAYAAAADcQB+AAh0AAltZXRhLXR5cGV0AA9zdG9yZWQtZW5jaGFudHN1cQB+AAYAAAADdAAISXRlbU1ldGF0AAlFTkNIQU5URURzcgA3Y29tLmdvb2dsZS5jb21tb24uY29sbGVjdC5JbW11dGFibGVCaU1hcCRTZXJpYWxpemVkRm9ybQAAAAAAAAAAAgAAeHEAfgADdXEAfgAGAAAAAXQADUJJTkRJTkdfQ1VSU0V1cQB+AAYAAAABc3EAfgAOAAAAAQ==', 'other', '', 0, '2022-10-07 20:03:04', '1970-01-01 08:00:00', '');"
                println(s)
            }
        }
    }
}