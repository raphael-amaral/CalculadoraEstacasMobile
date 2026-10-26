# Calculadora de Estacas

Aplicativo Android desenvolvido em Kotlin com Jetpack Compose para cálculo de distâncias entre estacas (20 m/estaca).

---

## Como gerar um arquivo `.apk` para instalar no celular

No Android, todo arquivo `.apk` precisa ser **assinado digitalmente** para que o sistema operacional permita a instalação. Existem duas formas de gerar uma versão instalável:

1. **APK de Debug (Mais rápido, ideal para uso pessoal e testes):** O próprio Android Studio / Gradle assina o aplicativo automaticamente com uma chave de desenvolvimento padrão.
2. **APK de Release Assinado (Ideal para distribuição final):** Uma versão otimizada e assinada com uma chave privada (Keystore) criada por você.

---

### Opção 1: Gerar APK de Debug (Mais simples)

#### Pelo Android Studio:
1. No menu superior, clique em **Build** > **Build Bundle(s) / APK(s)** > **Build APK(s)**.
2. Aguarde a conclusão do processo no canto inferior direito.
3. Quando aparecer a notificação *"Build APK(s): APK(s) generated successfully"*, clique no link **locate**.
4. O arquivo estará na pasta:
   `app/build/outputs/apk/debug/app-debug.apk`

#### Pelo Terminal (Windows PowerShell / CMD):
Na raiz do projeto, execute:
```powershell
.\gradlew.bat assembleDebug
```
O arquivo será gerado em:
`app\build\outputs\apk\debug\app-debug.apk`

---

### Opção 2: Gerar APK de Release Assinado (Versão final de distribuição)

Se quiser gerar uma versão final (`release`) sem os recursos de depuração:

1. No Android Studio, vá em **Build** > **Generate Signed App Bundle / APK...**.
2. Selecione a opção **APK** e clique em **Next**.
3. Em **Key store path**:
   - Se ainda não tiver uma chave, clique em **Create new...**, escolha onde salvar o arquivo `.jks`, defina uma senha para o Keystore, um apelido (*alias*) e senha para a chave, preencha ao menos um campo do certificado (ex: seu nome) e clique em **OK**.
   - Se já tiver uma chave criada, clique em **Choose existing...** e selecione-a.
4. Clique em **Next**.
5. Em **Build Variants**, selecione **release** e clique em **Create**.
6. Ao finalizar, o APK assinado estará disponível em:
   `app/release/app-release.apk` (ou `app/build/outputs/apk/release/app-release.apk`).

---

## Como instalar o `.apk` no celular

1. **Transfira o arquivo `.apk` para o celular:**
   - Você pode enviar pelo WhatsApp, Telegram, Google Drive, e-mail ou transferir via cabo USB.
2. **Abra o arquivo no celular:**
   - Use o gerenciador de arquivos do aparelho ou toque no arquivo recebido no aplicativo onde o baixou.
3. **Permita a instalação de fontes desconhecidas (se solicitado):**
   - Como o aplicativo não veio da Google Play Store, o Android pedirá permissão para *"Instalar apps desconhecidos"* para o aplicativo que você usou para abrir o APK (ex: Arquivos, Chrome, WhatsApp). Ative a permissão e confirme.
4. **Aviso do Google Play Protect:**
   - Caso apareça um aviso dizendo que o app não é reconhecido pelo Play Protect (comum em apps de estudo ou assinados com chave própria/debug), toque em **Mais detalhes** e depois em **Instalar assim mesmo**.
5. Conclua a instalação e abra o aplicativo normalmente!
