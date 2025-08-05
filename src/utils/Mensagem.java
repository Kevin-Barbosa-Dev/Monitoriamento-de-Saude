package utils;

public class Mensagem {
    // Cadastro
    public static void mensagemSucesso() {
        System.out.println("Cadastro realizado com sucesso!");
    }

    public static void mensagemCpfExistente() {
        System.out.println("CPF já cadastrado!");
    }

    public static void mensagemEmailExistente() {
        System.out.println("Email já cadastrado!");
    }

    public static void mensagemCrmExistente() {
        System.out.println("CRM já cadastrado!");
    }

    public static void mensagemOpcaoInvalida() {
        System.out.println("Opção inválida. Por favor, tente novamente.");
    }

    // Geral
    public static String mensagemSair() {
        return "Saindo...";
    }

    public static String mensagemValorInvalido() {
        return "Índice inválido! Tente novamente.";
    }

    public static void mensagemAlertaFinalizado() {
        System.out.println("Alerta finalizado com sucesso!");
    }

    public static void mensagemAlertaSucesso() {
        System.out.println("Alerta criado com sucesso!");
    }

    public static void mensagemAlertaErro() {
        System.out.println("Erro ao criar o alerta.");
    }

    public static void mostrarMensagemSemAlertas() {
        System.out.println("Não há alertas para exibir.");
    }

    public static void mensagemConsultaSucesso() {
        System.out.println("\nConsulta agendada com sucesso.");
    }

    public static void mensagemConsultaCancelada() {
        System.out.println("\nConsulta cancelada com sucesso.");
    }

    public static void mensagemDeLista() {
        System.out.println("--- Lista de Dispositivos ---");
    }

    public static void mensagemDeRemovido() {
        System.out.println("Dispositivo removido com sucesso!");
    }

    public static void mensagemDeAtualizado() {
        System.out.println("Dispositivo atualizado com sucesso!");
    }

    public static void mensagemNumeroInvalido() {
        System.out.println("Número inválido!");
    }

    // Login
    public static void mensagemAposLogin(Object usuario) {
        if (usuario != null) {
            System.out.println("Login bem-sucedido!");
        } else {
            System.out.println("Credenciais inválidas. Tente novamente.");
        }
    }

    public static void mensagemErroAutenticacao() {
        System.out.println("Erro ao autenticar usuário. Verifique suas credenciais.");
    }

    // Medicamento
    public static void mensagemMedicamentoPrescrito(Object medicamento) {
        System.out.println("Medicamento prescrito com sucesso!");
    }

    public static void mensagemDosagemAjustada(Object medicamento) {
        System.out.println("Dosagem ajustada com sucesso!");
    }

    public static void mensagemMedicamentoNaoEncontrado() {
        System.out.println("Medicamento não encontrado.");
    }

    public static void mensagemMedicamentoAtualizado() {
        System.out.println("Medicamento atualizado com sucesso!");
    }

    public static void mensagemMedicamentoCancelado(Object medicamento) {
        System.out.println("Prescrição cancelada com sucesso!");
    }

    public static void mensagemMedicamentoNaoResgistrado() {
        System.out.println("Nenhum medicamento registrado para esse paciente.");
    }

    // Diagnostico
    public static void mensagemDiagnosticoCriado(Object diagnostico) {
        System.out.println("Diagnóstico criado com sucesso!");
    }

    public static void mensagemDiagnosticoAtualizado(Object diagnostico) {
        System.out.println("Diagnóstico atualizado com sucesso!");
    }

    public static void mensagemDiagnosticoCancelado(Object diagnostico) {
        System.out.println("Diagnóstico cancelado com sucesso!");
    }

    public static void mensagemDiagnosticoNaoEncontrado() {
        System.out.println("Nenhum diagnostico registrado para esse paciente.");
    }
    // Medico

    public static void mensagemLista() {
        System.out.println("\n--- Lista de Pacientes ---");
    }

    public static void mensagemNaoHaConsultas() {
        System.out.println("Não há consultas agendadas.");
    }

    public static void mensagemConflitoDeHorario() {
        System.out.println("Já existe uma consulta agendada para esse horário.");
    }

    public static void mensagemNaoHaMedicos() {
        System.out.println("Não há médicos disponíveis para agendamento.");
    }

    public static void mensagemNenhumPaciente() {
        System.out.println("Nenhum paciente cadastrado.");
    }

    // BaseController
    public static String mensagemParaConfirmar() {
        return "Deseja confirmar? (1 - Sim, 2 - Não)";
    }

    public static String mensagemDigitaUm() {
        return "1";
    }

    public static String mensagemDigitaS() {
        return "s";
    }

    public static String mensagemDigitaSim() {
        return "sim";
    }

    public static String mensagemDigitaDois() {
        return "1";
    }

    public static String mensagemDigitaN() {
        return "n";
    }

    public static String mensagemDigitaNaoSemAU() {
        return "nao";
    }

    public static String mensagemDigitaNao() {
        return "não";
    }

    public static String mensagemInvalidaDigUmOuDois() {
        return "Entrada inválida. Por favor digite 1 para Sim ou 2 para Não";
    }

    public static int listaNumerica() {
        int listaNum = 1;
        return listaNum++;
    }
}
