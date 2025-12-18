package com.example.nebeng.feature_a_homepage.domain.usecase

import com.example.nebeng.core.common.Result
import com.example.nebeng.feature_terminal.data.repository.TerminalRepository
import com.example.nebeng.feature_terminal.domain.model.TerminalSummary
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTerminalUseCase @Inject constructor(
    private val repository: TerminalRepository
) {
    suspend operator fun invoke(token: String): Flow<Result<List<TerminalSummary>>> {
        return repository.getAllTerminalSummary(token)
    }
}