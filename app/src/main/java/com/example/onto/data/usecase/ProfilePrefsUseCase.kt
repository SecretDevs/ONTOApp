package com.example.onto.data.usecase

import android.content.Context
import androidx.core.content.edit
import at.favre.lib.armadillo.Armadillo
import at.favre.lib.armadillo.AuthenticatedEncryption
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ProfilePrefsUseCase {
    fun setFirstName(firstName: String)
    fun setLastName(lastName: String)
    fun setEmail(email: String)
    fun setPhone(phone: String)
    fun getFirstName(): String
    fun getLastName(): String
    fun getEmail(): String
    fun getPhone(): String
    fun setUsageForDelivery(usableForDelivery: Boolean)
    fun setUserCity(city: String)
    fun setUserStreet(user: String)
    fun setUserHouse(house: String)
    fun setUserBuilding(building: String)
    fun setUserApartment(apartment: String)
    fun isUsingForDelivery(): Boolean
    fun getUserCity(): String
    fun getUserStreet(): String
    fun getUserHouse(): String
    fun getUserBuilding(): String
    fun getUserApartment(): String
}

class ProfilePrefsUseCaseImpl @Inject constructor(
    @ApplicationContext context: Context
) : ProfilePrefsUseCase {
    private val pref = Armadillo.create(context, PREF_NAME)
        .encryptionFingerprint(context)
        .encryptionKeyStrength(AuthenticatedEncryption.STRENGTH_VERY_HIGH)
        .build()

    override fun setFirstName(firstName: String) {
        pref.edit {
            putString(FIRST_NAME_FIELD, firstName)
        }
    }

    override fun setLastName(lastName: String) {
        pref.edit {
            putString(LAST_NAME_FIELD, lastName)
        }
    }

    override fun setEmail(email: String) {
        pref.edit {
            putString(EMAIL_FIELD, email)
        }
    }

    override fun setPhone(phone: String) {
        pref.edit {
            putString(PHONE_FIELD, phone)
        }
    }

    override fun getFirstName(): String = pref.getString(FIRST_NAME_FIELD, "") ?: ""

    override fun getLastName(): String = pref.getString(LAST_NAME_FIELD, "") ?: ""

    override fun getEmail(): String = pref.getString(EMAIL_FIELD, "") ?: ""

    override fun getPhone(): String = pref.getString(PHONE_FIELD, "") ?: ""

    override fun isUsingForDelivery(): Boolean = pref.getBoolean(FOR_DELIVERY_FIELD, false)

    override fun setUsageForDelivery(usableForDelivery: Boolean) {
        pref.edit {
            putBoolean(FOR_DELIVERY_FIELD, usableForDelivery)
        }
    }

    override fun setUserCity(city: String) {
        pref.edit {
            putString(DELIVERY_CITY_FIELD, city)
        }
    }

    override fun setUserStreet(user: String) {
        pref.edit {
            putString(DELIVERY_STREET_FIELD, user)
        }
    }

    override fun setUserHouse(house: String) {
        pref.edit {
            putString(DELIVERY_HOUSE_FIELD, house)
        }
    }

    override fun setUserBuilding(building: String) {
        pref.edit {
            putString(DELIVERY_BUILDING_FIELD, building)
        }
    }

    override fun setUserApartment(apartment: String) {
        pref.edit {
            putString(DELIVERY_APARTMENT_FIELD, apartment)
        }
    }

    override fun getUserCity(): String = pref.getString(DELIVERY_CITY_FIELD, "") ?: ""

    override fun getUserStreet(): String = pref.getString(DELIVERY_STREET_FIELD, "") ?: ""

    override fun getUserHouse(): String = pref.getString(DELIVERY_HOUSE_FIELD, "") ?: ""

    override fun getUserBuilding(): String = pref.getString(DELIVERY_BUILDING_FIELD, "") ?: ""

    override fun getUserApartment(): String = pref.getString(DELIVERY_APARTMENT_FIELD, "") ?: ""

    companion object {
        private const val PREF_NAME = "ONTO_PROFILE_PREF"
        private const val FIRST_NAME_FIELD = "ONTO_FIRST_NAME"
        private const val LAST_NAME_FIELD = "ONTO_LAST_NAME"
        private const val EMAIL_FIELD = "ONTO_EMAIL"
        private const val PHONE_FIELD = "ONTO_PHONE"
        private const val FOR_DELIVERY_FIELD = "ONTO_FOR_DELIVERY"
        private const val DELIVERY_CITY_FIELD = "ONTO_DELIVERY_CITY"
        private const val DELIVERY_STREET_FIELD = "ONTO_DELIVERY_STREET"
        private const val DELIVERY_HOUSE_FIELD = "ONTO_DELIVERY_HOUSE"
        private const val DELIVERY_BUILDING_FIELD = "ONTO_DELIVERY_BUILDING"
        private const val DELIVERY_APARTMENT_FIELD = "ONTO_DELIVERY_APARTMENT"
    }

}
