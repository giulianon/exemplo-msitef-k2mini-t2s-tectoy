# flutter_tef_msitef

Exemplo de integração com o m-Sitef que é o client do Sitef para Android (via intent). 

Esse exemplo foi testado no K2 Mini e T2s com um pinpad Gertec PPC 930 USB.

## Passos

1 - Instalar e executar o SitDemo que pode ser baixado do portal do cliente da Software Express. Ele é o servidor e deve ser instalado em uma máquina windows.

2 - Instalar e executar o m-Sitef (apk) que pode ser baixado do portal do cliente da Software Express. Esse é o client do Sitef e deve ser instalado no equipamento (K2 Mini ou T2s).

Observação: O m-Sitef ao ser chamado vai acessar o pinpad. No K2 Mini apareceu a tela de solicitação para dar a permissão. No T2s não apareceu. Caso isso aconteça basta ir nas configurações do app m-Sitef e dar as permissões. 

3 - Configurar no arquivo com/example/flutter_tef_msitef/MainActivity.kt o endereço ip do SitDemo. Essa configuração de IP e todas as demais deveriam vir via parâmetro, mas pra deixar bem simples fixei direto no arquivo mesmo. Fique a vontade pra melhorar o exemplo :)