
package controller;

import java.util.InputMismatchException;
import java.util.Scanner;
import model.Paciente;
import utils.Mensagem;
import utils.PacienteInputType;
import view.PacienteView;
import java.time.LocalDate;

public class PacienteController extends BaseController<Paciente> {
    private Paciente paciente;
    private ConsultaController consultaController;
    private PacienteView pacienteView;
    private Scanner ler;
    private DispositivoController dispositivoController;

    public PacienteController(Paciente paciente) {
        this.paciente = paciente;
        this.consultaController = new ConsultaController(paciente);
        this.pacienteView = new PacienteView();
        this.ler = new Scanner(System.in);
        this.dispositivoController = new DispositivoController();
    }

    public void dadosPaciente() {
        pacienteView.exibirDados(paciente);
    }

    @Override
    public void alterarDados(Paciente paciente) {
        int opcao;
        do {
            try {
                opcao = pacienteView.selecionarQualAlterar();
                String novoDado = null;
                switch (opcao) {
                    case 1:
                        pacienteView.solicitarInput(PacienteInputType.NOME);
                        novoDado = ler.nextLine();
                        if (confirmarAlteracao()) {
                            paciente.setNome(novoDado);
                        }
                        break;
                    case 2:
                        pacienteView.solicitarInput(PacienteInputType.CPF);
                        novoDado = ler.nextLine();
                        if (confirmarAlteracao()) {
                            paciente.setCpf(novoDado);
                        }
                        break;
                    case 3:
                        novoDado = pacienteView.solicitarInput(PacienteInputType.DATA_NASCIMENTO);
                        if (novoDado != null && confirmarAlteracao()) {
                            LocalDate novaData = pacienteView.parseDataNascimento(novoDado);
                            paciente.setDataNascimento(novaData);
                        }
                        break;
                    case 4:
                        pacienteView.solicitarInput(PacienteInputType.ENDERECO);
                        novoDado = ler.nextLine();
                        if (confirmarAlteracao()) {
                            paciente.setEndereco(novoDado);
                        }
                        break;
                    case 5:
                        pacienteView.solicitarInput(PacienteInputType.TELEFONE);
                        novoDado = ler.nextLine();
                        if (confirmarAlteracao()) {
                            paciente.setTelefone(novoDado);
                        }
                        break;
                    case 6:
                        pacienteView.solicitarInput(PacienteInputType.EMAIL);
                        novoDado = ler.nextLine();
                        if (confirmarAlteracao()) {
                            paciente.setEmail(novoDado);
                        }
                        break;
                    case 7:
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
        } while (opcao != 7);
    }

    public void alterarEVoltar() {
        int opcao;
        do {
            opcao = pacienteView.opcoesAlterarEVoltar();
            switch (opcao) {
                case 1:
                    alterarDados(paciente);
                    break;
                case 2:
                    return;
                default:
                    Mensagem.mensagemOpcaoInvalida();
                    break;
            }
        } while (opcao != 2);
    }

    public void menu() {
        int opcao;
        do {
            opcao = pacienteView.menu();
            switch (opcao) {
                case 1:
                    dadosPaciente();
                    alterarEVoltar();
                    break;
                case 2:
                    consultaController.menuHistoricoMedico(ler);
                    break;
                case 3:
                    consultaController.consultaOpcoes(ler);
                    break;
                case 4:
                    dispositivoController.menu();
                    break;
                case 5:
                    Mensagem.mensagemSair();
                    return;
                default:
                    break;
            }
        } while (opcao != 5);
    }
}