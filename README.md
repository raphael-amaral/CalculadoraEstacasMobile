# Calculadora de Estacas 📐

Aplicativo Android moderno, ágil e intuitivo para cálculo de distâncias entre estacas topográficas em obras de infraestrutura, rodovias, ferrovias e terraplenagem.

<p align="center">
  <img src="docs/tela-atual.png" width="340" alt="Interface da Calculadora de Estacas" style="border-radius: 16px; box-shadow: 0 4px 12px rgba(0,0,0,0.15);" />
</p>

---

## 🏗️ O Problema e a Solução

Na engenharia civil e na agrimensura, os alinhamentos de vias e obras lineares são estaqueados com piquetes a cada **20 metros** (ou outro intervalo pré-determinado). 

Na rotina do canteiro de obras, encarregados, topógrafos e engenheiros frequentemente precisam calcular distâncias fracionadas entre estacas (exemplo: da **Estaca 14 + 12,50 m** até a **Estaca 48 + 5,20 m**). Fazer esses cálculos de cabeça ou em calculadoras comuns é passível de equívocos operacionais.

A **Calculadora de Estacas** resolve isso de forma instantânea e à prova de falhas:
- Permite informar o número da estaca e a fração em metros com validação em tempo real.
- Calcula a distância exata respeitando a geometria e o sentido do alinhamento.
- Oferece interface limpa, botões de ação rápida e funciona **100% offline**, ideal para áreas remotas e canteiros de obra.

---

## 🧮 Lógica de Cálculo

A distância entre duas estacas no estaqueamento linear é obtida pela fórmula:

$$\text{Distância (m)} = (\text{Estaca Final} - \text{Estaca Inicial}) \times \text{Metros por Estaca} + \text{Metros Final} - \text{Metros Inicial}$$

> **Exemplo prático:**  
> Da **Estaca 10 + 5,00 m** até a **Estaca 15 + 12,00 m** (com 20 m/estaca):  
> $\text{Distância} = (15 - 10) \times 20 + 12,00 - 5,00 = 100 + 7,00 = \mathbf{107,00\text{ m}}$.

---

## ✨ Principais Funcionalidades

- **Cálculo em Tempo Real:** Atualização imediata do resultado à medida que os campos são preenchidos.
- **Configuração de Espaçamento:** Ajuste do valor de metros por estaca (padrão de 20 metros, customizável no menu lateral).
- **Controle de Sentido:** Opção configurável para permitir ou restringir resultados negativos (quando a estaca inicial é maior que a final).
- **Ações Rápidas com 1 Toque:**
  - 📋 **Copiar:** Copia o resultado formatado com unidade métrica para a área de transferência.
  - 🧹 **Limpar:** Reseta todos os campos de entrada de forma imediata.
- **Design Moderno com Material 3:** Cards visualmente destacados e numerados por etapas, cores temáticas intuitivas e cabeçalho curvo com degradê.
- **Suporte Edge-to-Edge:** Integração fluida com as bordas da tela, câmeras frontais e barras de gestos do Android.
- **Persistência Local Leve:** Configurações salvas automaticamente no aparelho via Jetpack DataStore (sem necessidade de banco de dados pesado).
- **Totalmente Offline & Seguro:** Sem dependência de conexão com internet, sem anúncios e sem coleta de dados do usuário.

---

## 🛠️ Tecnologias e Arquitetura

O projeto foi construído seguindo as melhores práticas e padrões recomendados pelo Google para desenvolvimento Android moderno:

- **Linguagem:** [Kotlin](https://kotlinlang.org/)
- **Interface Declarativa:** [Jetpack Compose](https://developer.android.com/jetpack/compose) com [Material Design 3](https://m3.material.io/)
- **Arquitetura:** MVVM (Model-View-ViewModel) + Princípios de Clean Architecture:
  - **Domínio Puro (`domain`):** Regras matemáticas desacopladas de qualquer dependência do framework Android.
  - **Camada de Apresentação (`ui`):** Componentes Compose modulares orientados a estados unidirecionais (*UDF*).
- **Gerenciamento de Estado:** `StateFlow` e Kotlin Coroutines.
- **Persistência de Dados:** [Jetpack DataStore Preferences](https://developer.android.com/topic/libraries/architecture/datastore).
- **Testes Automatizados:** Testes unitários com JUnit 4 cobrindo casos nominais e situações de borda da lógica de cálculo.

---

## 📲 Como Instalar no Celular

### Opção 1: Download Direto (Recomendado)
1. Acesse a aba de [**Releases**](https://github.com/raphael-amaral/CalculadoraEstacasMobile/releases) no GitHub.
2. Baixe o arquivo `.apk` da versão mais recente no seu celular.
3. Abra o arquivo baixado e confirme a instalação (caso o Android solicite permissão para instalar fontes desconhecidas, autorize).

### Opção 2: Compilar a partir do Código-Fonte
Caso queira clonar e compilar o projeto na sua máquina:

1. Clone o repositório:
   ```bash
   git clone https://github.com/raphael-amaral/CalculadoraEstacasMobile.git
   cd CalculadoraEstacasMobile
   ```
2. Gere o APK de depuração via Gradle:
   ```powershell
   .\gradlew.bat assembleDebug
   ```
3. O APK gerado estará em:
   `app/build/outputs/apk/debug/app-debug.apk`

---

## 👤 Autor

Desenvolvido por **Raphael do Amaral Nunes**.

- GitHub: [@raphael-amaral](https://github.com/raphael-amaral)
- Repositório do Projeto: [CalculadoraEstacasMobile](https://github.com/raphael-amaral/CalculadoraEstacasMobile)
