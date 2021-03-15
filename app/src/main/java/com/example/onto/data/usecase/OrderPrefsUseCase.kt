package com.example.onto.data.usecase

import android.content.Context
import androidx.core.content.edit
import at.favre.lib.armadillo.Armadillo
import at.favre.lib.armadillo.AuthenticatedEncryption
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface OrderPrefsUseCase {
    fun canNavigateNext(): Boolean
    fun setCanNavigateNext(canNavigateNext: Boolean)
    fun setFirstName(firstName: String)
    fun setLastName(lastName: String)
    fun setEmail(email: String)
    fun setPhone(phone: String)
    fun getFirstName(): String
    fun getLastName(): String
    fun getEmail(): String
    fun getPhone(): String
    fun setDeliveryType(deliveryType: Int)
    fun setUserCity(city: String)
    fun setUserStreet(user: String)
    fun setUserHouse(house: String)
    fun setUserBuilding(building: String)
    fun setUserApartment(apartment: String)
    fun setPickupPoint(pickupPoint: String)
    fun setCommentary(commentary: String)
    fun getDeliveryType(): Int
    fun getUserCity(): String
    fun getUserStreet(): String
    fun getUserHouse(): String
    fun getUserBuilding(): String
    fun getUserApartment(): String
    fun getPickupPoint(): String
    fun getCommentary(): String
}

class OrderPrefsUseCaseImpl @Inject constructor(
    @ApplicationContext context: Context
) : OrderPrefsUseCase {
    private val pref = Armadillo.create(context, PREF_NAME)
        .encryptionFingerprint(context)
        .encryptionKeyStrength(AuthenticatedEncryption.STRENGTH_VERY_HIGH)
        .build()

    override fun canNavigateNext(): Boolean = pref.getBoolean(PREF_CAN_NEXT, false)

    override fun setCanNavigateNext(canNavigateNext: Boolean) {
        pref.edit {
            putBoolean(PREF_CAN_NEXT, canNavigateNext)
        }
    }

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

    override fun setDeliveryType(deliveryType: Int) {
        pref.edit {
            putInt(DELIVERY_TYPE_FIELD, deliveryType)
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

    override fun setPickupPoint(pickupPoint: String) {
        pref.edit {
            putString(DELIVERY_PICKUP_POINT_FIELD, pickupPoint)
        }
    }

    override fun setCommentary(commentary: String) {
        pref.edit {
            putString(DELIVERY_COMMENTARY_FIELD, commentary)
        }
    }

    override fun getDeliveryType(): Int = pref.getInt(DELIVERY_TYPE_FIELD, -1)

    override fun getUserCity(): String = pref.getString(DELIVERY_CITY_FIELD, "") ?: ""

    override fun getUserStreet(): String = pref.getString(DELIVERY_STREET_FIELD, "") ?: ""

    override fun getUserHouse(): String = pref.getString(DELIVERY_HOUSE_FIELD, "") ?: ""

    override fun getUserBuilding(): String = pref.getString(DELIVERY_BUILDING_FIELD, "") ?: ""

    override fun getUserApartment(): String = pref.getString(DELIVERY_APARTMENT_FIELD, "") ?: ""

    override fun getPickupPoint(): String = pref.getString(DELIVERY_PICKUP_POINT_FIELD, "") ?: ""

    override fun getCommentary(): String = pref.getString(DELIVERY_PICKUP_POINT_FIELD, "") ?: ""

    companion object {
        private const val PREF_NAME = "ONTO_ORDER_PREF"
        private const val PREF_CAN_NEXT = "ONTO_CAN_NEXT"
        private const val FIRST_NAME_FIELD = "ONTO_FIRST_NAME"
        private const val LAST_NAME_FIELD = "ONTO_LAST_NAME"
        private const val EMAIL_FIELD = "ONTO_EMAIL"
        private const val PHONE_FIELD = "ONTO_PHONE"
        private const val DELIVERY_TYPE_FIELD = "ONTO_DELIVERY_TYPE"
        private const val DELIVERY_CITY_FIELD = "ONTO_DELIVERY_CITY"
        private const val DELIVERY_STREET_FIELD = "ONTO_DELIVERY_STREET"
        private const val DELIVERY_HOUSE_FIELD = "ONTO_DELIVERY_HOUSE"
        private const val DELIVERY_BUILDING_FIELD = "ONTO_DELIVERY_BUILDING"
        private const val DELIVERY_APARTMENT_FIELD = "ONTO_DELIVERY_APARTMENT"
        private const val DELIVERY_PICKUP_POINT_FIELD = "ONTO_PICKUP_POINT_APARTMENT"
        private const val DELIVERY_COMMENTARY_FIELD = "ONTO_DELIVERY_COMMENTARY"
    }

}