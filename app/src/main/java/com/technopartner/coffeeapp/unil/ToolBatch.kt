package com.technopartner.coffeeapp.unil

import java.text.NumberFormat

class ToolBatch {
    companion object {
        val TAG = "ToolBatch"

        fun formatHarga(harga: Long?): String {
            return if (harga != null) {
                val price = formatThousand(harga)
                if (harga < 0) {
                    "-Rp${price.replace("-", "")}"
                } else {
                    "Rp$price"
                }
            } else {
                ""
            }
        }

        fun formatThousand(number: Long?): String {
            return if (number != null) {
                NumberFormat.getInstance().format(number)
            } else {
                ""
            }
        }

    }
}
