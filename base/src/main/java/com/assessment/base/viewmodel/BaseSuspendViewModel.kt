package com.assessment.base.viewmodel

import androidx.lifecycle.viewModelScope
import com.assessment.base.utils.MsgUtil.somethingWentWrong
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseSuspendViewModel : BaseViewModel() {

    protected fun executedSuspendedCodeBlock(
        operationTag: String = String(),
        codeBlock: suspend () -> Any,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = codeBlock()
                onSuspendResponse(operationTag, data)
            } catch (exception: Exception) {
                dataLoading.set(false)
                _showMessage.emit(somethingWentWrong)
            }
        }
    }

    abstract fun onSuspendResponse(operationTag: String, resultResponse: Any)
}
