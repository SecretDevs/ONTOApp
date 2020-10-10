package com.example.onto

class ExampleRepository {

    fun clearCounter(): ExampleEffect {
        ExampleActivity.examplePrefs.let {
            it?.edit()
                ?.putInt("first", 0)
                ?.putInt("second", 0)
                ?.commit()
        }
        return ExampleEffect.UpdateCounter(0, 0)
    }

    fun updateCounter(addValue: Int, counterNumber: Int): ExampleEffect {
        val name = if (counterNumber == 1) "first" else "second"
        ExampleActivity.examplePrefs.let {
            it?.edit()
                ?.putInt(name, it.getInt(name, 0) + addValue)
                ?.commit()
        }
        return ExampleEffect.UpdateCounter(
            valueFirst = ExampleActivity.examplePrefs?.getInt("first", 0) ?: 0,
            valueSecond = ExampleActivity.examplePrefs?.getInt("second", 0) ?: 0
        )
    }

}