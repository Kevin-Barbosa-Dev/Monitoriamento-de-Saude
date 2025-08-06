package controller;

import model.*;
import utils.ConsultaUtils;
import utils.MedicoInputType;
import utils.Mensagem;
import view.ConsultaView;
import view.MedicamentoView;
import view.MedicoView;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MedicoController extends BaseController<Medico> {
    private Scanner sc;
    private Medico medico;
    private MedicoView medicoView;
    private ConsultaView view;
    private DispositivoController dispositivoController;
    private UsuarioRepositorio repositorio;
    private MedicamentoView medicamentoView;
    private AlertaController alertController;
    private MonitoramentoController monitoramentoController;
    private MedicamentoController medicamentoController;

    public MedicoController(Medico medico) {
        this.medico = medico;
        this.medicoView = new MedicoView();
        this.view = new ConsultaView();
        this.dispositivoController = new DispositivoController();
        this.sc = new Scanner(System.in);
        this.repositorio = UsuarioRepositorio.getInstance();
        this.medicamentoView = new MedicamentoView();
        this.alertController = new AlertaController(medico);
        this.monitoramentoController = new MonitoramentoController(medico);

    }

    public void dadosMedico() {
        medicoView.exibirDados(medico);
    }

    @Override
    public void alterarDados(Medico medico) {
        int opcao;
        do {
            try {
                opcao = medicoView.selecionarQualAlterar();
                String novoDado = null;

                switch (opcao) {
                    case 1:
                        medicoView.solicitarInput(MedicoInputType.NOME);
                        novoDado = sc.nextLine();
                        if (confirmarAlteracao()) {
                            medico.setNome(novoDado);
                        }
                        break;
                    case 2:
                        medicoView.solicitarInput(MedicoInputType.ESPECIALIDADE);
                        novoDado = sc.nextLine();
                        if (confirmarAlteracao()) {
                            medico.setEspecialidade(novoDado);
                        }
                        break;
                    case 3:
                        medicoView.solicitarInput(MedicoInputType.CRM);
                        novoDado = sc.nextLine();
                        if (confirmarAlteracao()) {
                            medico.setCrm(novoDado);
                        }
                        break;
                    case 4:
                        medicoView.solicitarInput(MedicoInputType.TELEFONE);
                        novoDado = sc.nextLine();
                        if (confirmarAlteracao()) {
                            medico.setTelefone(novoDado);
                        }
                        break;
                    case 5:
                        medicoView.solicitarInput(MedicoInputType.EMAIL);
                        novoDado = sc.nextLine();
                        if (confirmarAlteracao()) {
                            medico.setEmail(novoDado);
                        }
                        break;
                    case 6:
                        return;
                    default:
                        Mensagem.mensagemOpcaoInvalida();
                        break;
                }
            } catch (InputMismatchException e) {
                Mensagem.mensagemOpcaoInvalida();
                ler.nextLine();
                opcao = 0;
                continue;
            }
        } while (opcao != 6);
    }

    public void alterarEVoltar() {
        int opcao;
        do {
            opcao = medicoView.opcoesAlterarEVoltar();
            switch (opcao) {
                case 1:
                    alterarDados(medico);
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 2);
    }

    public void menu() {
        int opcao;
        do {
            opcao = medicoView.menu();
            switch (opcao) {
                case 1:
                    dadosMedico();
                    alterarEVoltar();
                    break;
                case 2:
                    selecionarPacienteExibirPlano();
                    break;
                case 3:
                    consultarAgendamentosESelecionar();
                    break;
                case 4:
                    dispositivoController.menu();
                    break;
                case 5:
                    alertController.alertaMenu();
                    break;
                case 6:
                    monitoramentoController.menuMonitoramento();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 7);
    }

    public void consultarAgendamentosESelecionar() {
        List<Consulta> consultas = medico.getConsultas();

        consultas.stream()
                .forEach(this::exibirConsulta);

        int opcao = medicoView.selecionarConsulta();
        selecionarConsultaListadaEOpcoes(opcao, consultas);
    }

    private void exibirConsulta(Consulta consulta) {
        int num = 1;
        ConsultaUtils.exibirConsultaBasica(consulta, view, num);
    }

    private int selecionarConsultaListadaEOpcoes(int opcao, List<Consulta> consultas) {
        if (opcao < 0 || opcao > consultas.size()) {
            Mensagem.mensagemOpcaoInvalida();
            return -1;
        }
        Consulta consultaSelecionada = consultas.get(opcao - 1);
        medicoView.exibirDetalhesConsulta(
                consultaSelecionada.getPaciente().getNome(),
                consultaSelecionada.getDataConsulta(), consultaSelecionada.getDiagnostico(),
                consultaSelecionada.getPrescricao());
        exibirOpcoesConsulta(consultaSelecionada);
        return opcao;
    }

    private void exibirOpcoesConsulta(Consulta consulta) {
        int opcao;

        do {
            opcao = medicoView.mostrarOpcoesConsulta();
            switch (opcao) {
                case 1:
                    fazerDiagnostico(consulta);
                    break;
                case 2:
                    alterarDiagnosticoDaConsulta(consulta);
                    break;
                case 3:
                    registrarPrescricao(consulta);
                    break;
                case 4:
                    break;
                default:
                    Mensagem.mensagemOpcaoInvalida();
                    break;
            }
        } while (opcao != 4);
    }

    private void fazerDiagnostico(Consulta consulta) {
        Diagnostico diagnostico = medicoView.formDiagnostico(consulta.getPaciente().getNome());
        consulta.getDiagnostico().add(diagnostico);
        Mensagem.mensagemDiagnosticoCriado(diagnostico);
    }

    private Diagnostico alterarDiagnosticoDaConsulta(Consulta consulta) {
        List<Diagnostico> diagnosticos = consulta.getDiagnostico();

        if (!diagnosticos.isEmpty()) {
            for (int i = 0; i < diagnosticos.size(); i++) {
                medicoView.listarDiagnosticos(diagnosticos, i);
            }

            int escolha = medicoView.escolherDiagnostico(diagnosticos);
            if (escolha < 1 || escolha > diagnosticos.size()) {
                Mensagem.mensagemOpcaoInvalida();
                return null;
            }
            Diagnostico novoDiagnostico = medicoView.formAlterarDiagnostico(consulta.getPaciente().getNome());
            diagnosticos.set(escolha - 1, novoDiagnostico);
            Mensagem.mensagemDiagnosticoAtualizado(novoDiagnostico);
            return novoDiagnostico;
        }
        Mensagem.mensagemDiagnosticoNaoEncontrado();
        return null;

    }

    private void registrarPrescricao(Consulta consulta) {
        Medicamento medicamento = medicamentoView.formPrescreverMedicamento();

        if (medicamento != null) {
            consulta.adicionarMedicamento(medicamento);
            consulta.getPaciente().adicionarAoPlano(medicamento);
            Mensagem.mensagemMedicamentoPrescrito(medicamento.getNome());
        } else {
            Mensagem.mensagemMedicamentoNaoResgistrado();
        }
    }

    public Paciente selecionarPacienteExibirPlano() {
        List<UsuarioModel> usuarios = repositorio.getUsuarios();
        List<Paciente> pacientes = new ArrayList<>();

        for (UsuarioModel usuario : usuarios) {
            if (usuario instanceof Paciente) {
                pacientes.add((Paciente) usuario);
            }
        }

        if (pacientes.isEmpty()) {
            Mensagem.mensagemNenhumPaciente();
            return null;
        }
        int i = 1;
        Mensagem.mensagemLista();
        for (Paciente paciente : pacientes) {
            medicoView.exibirPacientes(i++, paciente.getNome(), paciente.getCpf());
        }

        // Selecionar paciente
        int escolha = medicoView.selecionarUmPaciente();
        if (escolha == 0) {
            return null; // Voltar
        }

        if (escolha < 1 || escolha > pacientes.size()) {
            System.out.println("Opção inválida! Tente novamente.");
            return null;
        }
        Paciente pacienteSelecionado = pacientes.get(escolha - 1);
        medicoView.perfilDoPaciente(
                pacienteSelecionado.getNome(),
                pacienteSelecionado.getCpf(),
                pacienteSelecionado.getDataNascimento(),
                pacienteSelecionado.getTelefone(),
                pacienteSelecionado.getEndereco(),
                pacienteSelecionado.getEmail());
        monitoramentoController.Analise(pacienteSelecionado);
        return pacienteSelecionado;
    }

    public void exibirPlanoTratamento(Paciente paciente) {
        System.out.println("Plano de Tratamento do Paciente " + paciente.getNome() + ":");
        if (paciente.getMedicamentos().isEmpty()) {
            System.out.println("Nenhum medicamento registrado.");
        } else {
            medicamentoController.exibirMedicamentos();
        }

    }
}
