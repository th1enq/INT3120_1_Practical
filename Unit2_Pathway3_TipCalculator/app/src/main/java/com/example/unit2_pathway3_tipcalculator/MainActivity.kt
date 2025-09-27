package com.example.unit2_pathway3_tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unit2_pathway3_tipcalculator.ui.theme.Unit2_Pathway3_TipCalculatorTheme
import java.text.NumberFormat
import kotlin.math.roundToInt

// Data class to hold tip calculation results
data class TipCalculationResult(
    val tipAmount: String,
    val totalAmount: String,
    val tipPercentage: Double
)

// Constants for tip percentages
object TipConstants {
    const val DEFAULT_TIP_PERCENT = 15.0
    val TIP_OPTIONS = listOf(15.0, 18.0, 20.0)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            Unit2_Pathway3_TipCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipCalculatorScreen(modifier: Modifier = Modifier) {
    var billAmountInput by remember { mutableStateOf("") }
    var selectedTipPercent by remember { mutableStateOf(TipConstants.DEFAULT_TIP_PERCENT) }
    var roundUp by remember { mutableStateOf(false) }

    val billAmount = billAmountInput.toDoubleOrNull() ?: 0.0
    val calculationResult = calculateTipResult(
        amount = billAmount,
        tipPercent = selectedTipPercent,
        roundUp = roundUp
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .safeDrawingPadding()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        TipCalculatorHeader()
        
        // Bill amount input
        BillAmountInputField(
            billAmount = billAmountInput,
            onBillAmountChange = { billAmountInput = it },
            modifier = Modifier.fillMaxWidth()
        )
        
        // Tip percentage selection
        TipPercentageSelector(
            selectedPercent = selectedTipPercent,
            onPercentSelected = { selectedTipPercent = it }
        )
        
        // Round up option
        RoundUpOption(
            roundUp = roundUp,
            onRoundUpChange = { roundUp = it }
        )
        
        // Results display
        TipResultsDisplay(
            calculationResult = calculationResult,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun TipCalculatorHeader() {
    Text(
        text = stringResource(R.string.calculate_tip),
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 24.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BillAmountInputField(
    billAmount: String,
    onBillAmountChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = billAmount,
        onValueChange = onBillAmountChange,
        label = { Text(stringResource(R.string.bill_amount)) },
        placeholder = { Text("0.00") },
        leadingIcon = { Text("$", style = MaterialTheme.typography.bodyLarge) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = modifier
    )
}

@Composable
private fun TipPercentageSelector(
    selectedPercent: Double,
    onPercentSelected: (Double) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Tip Percentage",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TipConstants.TIP_OPTIONS.forEach { percent ->
                    TipPercentageChip(
                        percent = percent,
                        isSelected = selectedPercent == percent,
                        onSelected = { onPercentSelected(percent) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TipPercentageChip(
    percent: Double,
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    FilterChip(
        selected = isSelected,
        onClick = onSelected,
        label = { 
            Text(
                text = "${percent.roundToInt()}%",
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        },
        modifier = Modifier.selectable(
            selected = isSelected,
            onClick = onSelected
        )
    )
}

@Composable
private fun RoundUpOption(
    roundUp: Boolean,
    onRoundUpChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Round up tip",
            style = MaterialTheme.typography.bodyLarge
        )
        Switch(
            checked = roundUp,
            onCheckedChange = onRoundUpChange
        )
    }
}

@Composable
private fun TipResultsDisplay(
    calculationResult: TipCalculationResult,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Calculation Results",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            Divider()
            
            ResultRow(
                label = "Tip (${calculationResult.tipPercentage.roundToInt()}%)",
                value = calculationResult.tipAmount
            )
            
            ResultRow(
                label = "Total Amount",
                value = calculationResult.totalAmount,
                isTotal = true
            )
        }
    }
}

@Composable
private fun ResultRow(
    label: String,
    value: String,
    isTotal: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = if (isTotal) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyLarge,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = value,
            style = if (isTotal) MaterialTheme.typography.titleLarge else MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End
        )
    }
}

private fun calculateTipResult(
    amount: Double,
    tipPercent: Double = TipConstants.DEFAULT_TIP_PERCENT,
    roundUp: Boolean = false
): TipCalculationResult {
    var tipAmount = (tipPercent / 100) * amount
    
    if (roundUp) {
        tipAmount = kotlin.math.ceil(tipAmount)
    }
    
    val totalAmount = amount + tipAmount
    
    return TipCalculationResult(
        tipAmount = NumberFormat.getCurrencyInstance().format(tipAmount),
        totalAmount = NumberFormat.getCurrencyInstance().format(totalAmount),
        tipPercentage = tipPercent
    )
}

@Preview(showBackground = true)
@Composable
fun TipCalculatorScreenPreview() {
    Unit2_Pathway3_TipCalculatorTheme {
        TipCalculatorScreen()
    }
}
