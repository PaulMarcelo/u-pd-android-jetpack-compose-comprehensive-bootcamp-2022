package ec.com.pmyb.jettipapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ec.com.pmyb.jettipapp.components.InputField
import ec.com.pmyb.jettipapp.ui.theme.JetTipAppTheme
import ec.com.pmyb.jettipapp.util.calculataTotalPerReson
import ec.com.pmyb.jettipapp.util.calculateTotalTip
import ec.com.pmyb.jettipapp.widget.RoundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MainContent()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    // A surface container using the 'background' color from the theme
    JetTipAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            content()
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun MainContent() {
    Column(modifier = Modifier.padding(12.dp)) {
        val range = IntRange(start = 0, endInclusive = 100)
        val splitByState = remember {
            mutableStateOf(1)
        }
        val tipAmountState = remember {
            mutableStateOf(0.0)
        }
        val totalPerPersonState = remember {
            mutableStateOf(0.0)
        }
        BillForm(
            range = range,
            splitByState = splitByState,
            tipAmountState = tipAmountState,
            totalPerPersonState = totalPerPersonState
        ) { billAmt ->
            Log.d("AMT", "MainContent: ${billAmt.toInt() * 100}")
        }
    }

}

@ExperimentalComposeUiApi
@Composable
fun BillForm(
    modifier: Modifier = Modifier,
    range: IntRange = 1..100,
    splitByState: MutableState<Int>,
    tipAmountState: MutableState<Double>,
    totalPerPersonState: MutableState<Double>,
    onValChange: (String) -> Unit = {}
) {

    val totalBillState = remember {
        mutableStateOf("")
    }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val keyBoardController = LocalSoftwareKeyboardController.current
    val sliderPoditionState = remember {
        mutableStateOf(0f)
    }
    val tipPercentage = (sliderPoditionState.value * 100).toInt()

    TopHeader(totalPerPerson = totalPerPersonState.value)

    Surface(
        modifier = modifier
            .padding(2.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(6.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            InputField(valueState = totalBillState,
                labelId = "Enter Bill",
                enable = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!validState) return@KeyboardActions
                    onValChange(totalBillState.value.trim())
                    keyBoardController?.hide()
                }
            )
            if (validState) {
                Row(
                    modifier = modifier.padding(3.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Split", modifier = Modifier.align(
                            alignment = Alignment.CenterVertically
                        )
                    )
                    Spacer(modifier = Modifier.width(120.dp))
                    Row(
                        modifier = Modifier.padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        RoundIconButton(
                            imageVector = Icons.Default.Remove,
                            onClick = {
                                splitByState.value =
                                    if (splitByState.value > 1) splitByState.value - 1
                                    else 1
                                totalPerPersonState.value = calculataTotalPerReson(
                                    totalBillState.value.toDouble(), tipPercentage = tipPercentage,
                                    splitBy = splitByState.value
                                )
                            }
                        )
                        Text(
                            text = "${splitByState.value}", modifier = Modifier
                                .align(
                                    alignment = Alignment.CenterVertically
                                )
                                .padding(start = 9.dp, end = 9.dp)
                        )
                        RoundIconButton(
                            imageVector = Icons.Default.Add,
                            onClick = {
                                if (splitByState.value < range.last) {
                                    splitByState.value = splitByState.value + 1
                                    totalPerPersonState.value = calculataTotalPerReson(
                                        totalBillState.value.toDouble(),
                                        tipPercentage = tipPercentage,
                                        splitBy = splitByState.value
                                    )
                                }
                            }
                        )
                    }
                }
                // Tip Row
                Row(modifier = modifier.padding(horizontal = 3.dp, vertical = 12.dp)) {
                    Text(
                        text = "Tip", modifier = Modifier.align(
                            alignment = Alignment.CenterVertically
                        )
                    )
                    Spacer(modifier = Modifier.width(200.dp))
                    Text(
                        text = "${tipAmountState.value}", modifier = Modifier.align(
                            alignment = Alignment.CenterVertically
                        )
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "$tipPercentage%")
                    Spacer(modifier = Modifier.height(14.dp))
                    //Slider
                    Slider(modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        value = sliderPoditionState.value, onValueChange = { newVale ->
                            sliderPoditionState.value = newVale
                            tipAmountState.value =
                                calculateTotalTip(
                                    totalBill = totalBillState.value.toDouble(),
                                    tipPercentage = tipPercentage
                                )
                            totalPerPersonState.value = calculataTotalPerReson(
                                totalBillState.value.toDouble(), tipPercentage = tipPercentage,
                                splitBy = splitByState.value
                            )
                        }, steps = 5,
                        onValueChangeFinished = {})

                }

            } else {
                Box {}
            }
        }
    }


}


//@Preview
@Composable
fun TopHeader(totalPerPerson: Double = 134.0) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .height(150.dp)
            .clip(
                shape = CircleShape.copy(all = CornerSize(12.dp))
                //.clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))
            ),
        color = Color(0xFFE9D7F7)

    ) {

        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val total = "%.2f".format(totalPerPerson)
            Text(text = "Total Per Person", style = MaterialTheme.typography.h5)
            Text(
                text = "$$total", style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.ExtraBold
            )

        }

    }
}


//@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetTipAppTheme {
        MyApp {
            Text("Hello again !")
        }
    }
}