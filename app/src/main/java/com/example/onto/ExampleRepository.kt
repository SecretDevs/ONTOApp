package com.example.onto

class ExampleRepository {

    fun getValues(): Pair<Int, Int> = (ExampleActivity.examplePrefs?.getInt("first", 0)
        ?: 0) to (ExampleActivity.examplePrefs?.getInt("second", 0) ?: 0)

    fun clearCounter(): Pair<Int, Int> {
        ExampleActivity.examplePrefs.let {
            it?.edit()
                ?.putInt("first", 0)
                ?.putInt("second", 0)
                ?.commit()
        }
        return getValues()
    }

    fun updateCounter(addValue: Int, counterNumber: Int): Pair<Int, Int> {
        val name = if (counterNumber == 1) "first" else "second"
        ExampleActivity.examplePrefs.let {
            it?.edit()
                ?.putInt(name, it.getInt(name, 0) + addValue)
                ?.commit()
        }
        return getValues()
    }

}