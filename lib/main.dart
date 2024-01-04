import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:sunmi_printer_plus/sunmi_printer_plus.dart';
// import 'package:permission_handler/permission_handler.dart';
import 'package:device_info_plus/device_info_plus.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});


  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Sitef',
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key});

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final MethodChannel _channel = MethodChannel('msitef');
  String _equipamento = 'Não identificado';

  Future<void> _pix() async {
    final String comprovante = await _channel.invokeMethod('pix');
    _imprimir(comprovante);
  }

  Future<void> _credito() async {
    final String comprovante = await _channel.invokeMethod('credito');
    _imprimir(comprovante);
  }

  Future<void> _debito() async {
    final String comprovante = await _channel.invokeMethod('debito');
    _imprimir(comprovante);
  }

  Future<void> _outros() async {
    final String comprovante = await _channel.invokeMethod('outros');
    _imprimir(comprovante);
  }

  Future<void> _adm() async {
    final String comprovante = await _channel.invokeMethod('adm');
    _imprimir(comprovante);
  }

  Future<void> _ativarImpressora() async {
    bool? result = await SunmiPrinter.bindingPrinter();
  }

  Future<void> _imprimir(String comprovante) async {
    await SunmiPrinter.startTransactionPrint(true);
    if (_equipamento.toUpperCase() == 'K2_MINI') {
      await SunmiPrinter.setCustomFontSize(20);
    } else {
      await SunmiPrinter.setCustomFontSize(30); // T2s
    }
    //  // k2 mini
    await SunmiPrinter.printText(comprovante);
    await SunmiPrinter.submitTransactionPrint(); // SUBMIT and cut paper
    await SunmiPrinter.cut(); // SUBMIT and cut paper
    await SunmiPrinter.exitTransactionPrint(true); // Clo
  }

  // Future<void> _requestPermission() async {
  //   final permission = Permission.phone;
  //
  //   if (await permission.isDenied) {
  //     await permission.request();
  //   }
  // }

  Future<void> _detectaEquipamento() async {
    DeviceInfoPlugin deviceInfo = DeviceInfoPlugin();
    AndroidDeviceInfo androidInfo = await deviceInfo.androidInfo;
    _equipamento = androidInfo.model;
  }

  @override
  void initState() {
    super.initState();
    _ativarImpressora();
    _detectaEquipamento();
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: Text('Sitef - ${_equipamento}'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            ElevatedButton(onPressed: () {
              _credito();
            }, child: const Text('Crédito')),
            ElevatedButton(onPressed: () {
              _debito();
            }, child: const Text('Débito')),
            ElevatedButton(onPressed: () {
              _pix();
            }, child: const Text('Pix')),
            ElevatedButton(onPressed: () {
              _outros();
            }, child: const Text('Outros')),
            ElevatedButton(
                onPressed: () {
                  _adm();
                },
                style: ElevatedButton.styleFrom(backgroundColor: Colors.red),
                child: const Text('Menu'),

            ),
            // ElevatedButton(onPressed: () {
            //   _requestPermission();
            // }, child: const Text('Permissão')),

          ],
        ),
      ),
    );
  }
}
