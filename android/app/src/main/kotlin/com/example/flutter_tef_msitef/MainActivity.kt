package com.example.flutter_tef_msitef

import android.app.Activity
import android.content.Intent
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

enum class TipoProcessamento {
    OUTROS,
    CREDITO,
    DEBITO,
    PIX,
    ADM

}

class MainActivity: FlutterActivity() {
    private val CHANNEL = "msitef"
    private lateinit var _result: MethodChannel.Result

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            _result = result
            if (call.method == "credito") {
                startActivityForResult(processar(TipoProcessamento.CREDITO), 222)
            } else {
                if (call.method == "debito") {
                    startActivityForResult(processar(TipoProcessamento.DEBITO), 222)
                } else {
                    if (call.method == "outros") {
                        startActivityForResult(processar(TipoProcessamento.OUTROS), 222)
                    } else {
                        if (call.method == "pix") {
                            startActivityForResult(processar(TipoProcessamento.PIX), 222)
                        } else {
                            if (call.method == "adm") {
                                startActivityForResult(processar(TipoProcessamento.ADM), 222)
                            } else {
                                result.notImplemented()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 222) {
            _result.success(data.getStringExtra("VIA_CLIENTE").toString())
        }
    }

    fun processar(tipoProcessamento: TipoProcessamento): Intent? {
        val data = Intent("br.com.softwareexpress.sitef.msitef.ACTIVITY_CLISITEF")
        data.putExtra("empresaSitef", "00000000")
        data.putExtra("CNPJ_CPF", "12345678909")
        data.putExtra("timeoutColeta", "30")
        data.putExtra("tipoPinpad", "ANDROID_USB")
        data.putExtra("comExterna", "0")
        data.putExtra("enderecoSitef", "192.168.0.20:4096") // Para rodar o exemplo aponte para o ip de onde estiver rodando o Sitdemo "192.168.0.20:4096"

        if (tipoProcessamento == TipoProcessamento.OUTROS) {
            data.putExtra("operador", "OPERADOR")
            data.putExtra("data", "20231228")
            data.putExtra("hora", "164500")
            data.putExtra("numeroCupom", "123456")
            data.putExtra("numParcelas", "1")
            data.putExtra("valor", "1000")

            data.putExtra("modalidade", "0")
        }
        if (tipoProcessamento == TipoProcessamento.DEBITO) {
            data.putExtra("operador", "OPERADOR")
            data.putExtra("data", "20231228")
            data.putExtra("hora", "164500")
            data.putExtra("numeroCupom", "123456")
            data.putExtra("numParcelas", "1")
            data.putExtra("valor", "1000")

            data.putExtra("restricoes", "TransacoesHabilitadas=16")
            data.putExtra("modalidade", "2")
        }
        if (tipoProcessamento == TipoProcessamento.CREDITO) {
            data.putExtra("operador", "OPERADOR")
            data.putExtra("data", "20231228")
            data.putExtra("hora", "164500")
            data.putExtra("numeroCupom", "123456")
            data.putExtra("numParcelas", "1")
            data.putExtra("valor", "1000")

            data.putExtra("restricoes", "TransacoesHabilitadas=26")
            data.putExtra("modalidade", "3")
        }
        if (tipoProcessamento == TipoProcessamento.PIX) {
            data.putExtra("operador", "OPERADOR")
            data.putExtra("data", "20231228")
            data.putExtra("hora", "164500")
            data.putExtra("numeroCupom", "123456")
            data.putExtra("numParcelas", "1")
            data.putExtra("valor", "1000")

            data.putExtra("restricoes", "CarteirasDigitaisHabilitadas=027160110024");
            data.putExtra("transacoesHabilitadas", "7;8;");
            data.putExtra("modalidade", "122")
        }

        if (tipoProcessamento == TipoProcessamento.ADM) {
            data.putExtra("modalidade", "110");
        }
        return data
    }
}






