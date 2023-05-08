package otus.homework.coroutines

import android.provider.ContactsContract

sealed class Result {
    class Success(data: ContactsContract.Contacts.Data): Result() {}

    class Error: Result() {}
    }

