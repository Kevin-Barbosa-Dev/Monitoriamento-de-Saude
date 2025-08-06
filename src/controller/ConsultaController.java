package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import utils.ConsultaUtils;
import utils.Mensagem;
import model.Consulta;
import model.Medico;
import model.Paciente;
import model.UsuarioModel;
import model.UsuarioRepositorio;
import view.ConsultaView;

public class ConsultaController {
    private ConsultaView view;
    private Paciente paciente;
    private List<Consulta> consultas;
    private List<Medico> medicosDisponiveis;

    public ConsultaController(Paciente paciente) {
        this.paciente = paciente;
        this.view = new ConsultaView();
        this.consultas = new ArrayList<>();
        this.medicosDisponiveis = new ArrayList<>();

        UsuarioRepositorio repositorio = UsuarioRepositorio.getInstance();
        for (UsuarioModel usuario : repositorio.getUsuarios()) {
            if (usuario instanceof Medico) {
                this.medicosDisponiveis.add((Medico) usuario);
            }
        }
    }

    public Consulta agendarConsulta() {
        LocalDate dataConsulta = view.obterDataConsulta();
        LocalTime horaConsulta = view.obterHoraConsulta();
        Medico medicoSelecionado = listaMedicosDisponiveis();

        if (verificarConflitoHorario(dataConsulta, horaConsulta, medicoSelecionado)) {
            Mensagem.mensagemConflitoDeHorario();
        }

        Consulta consulta = new Consulta(dataConsulta, horaConsulta, paciente, medicoSelecionado, null, null);
        consultas.add(consulta);
        if (medicoSelecionado != null) {
            confirmarOuCancelarConsulta(consulta);
        }
        return consulta;
    }

    public void confirmarConsulta(Consulta consulta) {
        if (consulta.getDataConsulta().isBefore(LocalDate.now())) {
            return;
        }
        if (consulta.getHoraConsulta().isBefore(LocalTime.of(8, 0)) ||
                consulta.getHoraConsulta().isAfter(LocalTime.of(17, 0))) {
            return;
        }
        // Adiciona a consulta ao histórico do paciente
        this.consultas.add(consulta);

        Medico medico = consulta.getMedico();
        if (medico != null) {
            if (medico.getConsultas() == null) {
                medico.setConsultas(new ArrayList<>());
            }
            medico.getConsultas().add(consulta);
            paciente.getHistoricoMedico().add(consulta);
            Mensagem.mensagemSucesso();
        }
    }

    private boolean verificarConflitoHorario(LocalDate dataConsulta, LocalTime horaConsulta, Medico medicoEscolhido) {
        for (Consulta consulta : consultas) {
            if (consulta.getDataConsulta().isEqual(dataConsulta) &&
                    consulta.getHoraConsulta().equals(horaConsulta) &&
                    consulta.getMedico().equals(medicoEscolhido) &&
                    !consulta.equals(consulta)) {
                return true;
            }
        }
        return false;
    }

    public Medico listaMedicosDisponiveis() {
        if (medicosDisponiveis.isEmpty()) {
            Mensagem.mensagemNaoHaMedicos();
            return null;
        }
        int i = 1;
        for (Medico medico : medicosDisponiveis) {
            view.escolherMedico(i++, medico.getNome(), medico.getEspecialidade());
        }
        int escolha = view.getMedicoEscolhido() - 1;
        return medicosDisponiveis.get(escolha);
    }

    public void cancelarConsulta(Consulta consulta) {
        this.consultas.remove(consulta);
        Mensagem.mensagemConsultaCancelada();
    }

    public void confirmarOuCancelarConsulta(Consulta consulta) {
        int opcao;
        do {
            opcao = view.opcoesDepoisDeAgendarConsulta();
            switch (opcao) {
                case 1:
                    confirmarConsulta(consulta);
                    return;
                case 2:
                    cancelarConsulta(consulta);
                    return;
                case 3:
                    return;
                default:
                    Mensagem.mensagemOpcaoInvalida();
            }
        } while (opcao != 3);
    }

    public void consultarAgendamentos() {
        List<Consulta> consultas = paciente.getHistoricoMedico();

        consultas.stream()
                .forEach(this::exibirAgendamentoSeValido);
    }

    private void exibirAgendamentoSeValido(Consulta consulta) {
        int i = 1;
        ConsultaUtils.exibirConsultaBasica(consulta, view, i++);
    }

    public void consultaOpcoes(Scanner ler) {
        int opcao;
        do {
            opcao = view.opcoesAgendarConsultarAgendamento();
            switch (opcao) {
                case 1:
                    agendarConsulta();
                    break;
                case 2:
                    consultarAgendamentos();
                    break;
                case 3:
                    return;
                default:
                    Mensagem.mensagemOpcaoInvalida();
            }
        } while (opcao != 4);

    }

    public void consultarHistorico(Paciente paciente) {
        List<Consulta> consultas = paciente.getHistoricoMedico();
        for (Consulta consulta : consultas) {
            if (consultas != null && !consultas.isEmpty() && consulta.getPaciente() != null
                    && consulta.getMedico() != null && consulta.getDiagnostico() != null
                    && consulta.getPrescricao() != null) {
                view.exibirHistoricoMedico(
                        consulta.getDataConsulta(),
                        consulta.getHoraConsulta(),
                        consulta.getPaciente().getNome(),
                        consulta.getMedico().getNome(),
                        consulta.getDiagnostico(),
                        consulta.getPrescricao());
            }
        }
    }

    public void menuHistoricoMedico(Scanner ler) {
        int opcao;
        do {
            opcao = view.menu(ler);
            switch (opcao) {
                case 1:
                    consultarHistorico(paciente);
                    break;
                case 2:
                    return;
                default:
                    Mensagem.mensagemOpcaoInvalida();
            }
        } while (opcao != 2);
    }

}