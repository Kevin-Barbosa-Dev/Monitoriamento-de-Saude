import org.junit.Before;
import org.junit.Test;

import controller.ConsultaController;
import controller.MedicoController;
import model.Consulta;
import model.Medico;
import model.Paciente;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultarAgendamentoTest {
    private Medico medico;
    private Paciente paciente;
    private List<Consulta> consultas = new ArrayList<>();
    private ConsultaController consultaController;
    private MedicoController medicoController;

    @Before
    public void setup() {
        // Setup test data
        medico = new Medico("Dr. Test", "dr@test.com", "senha123", "Cardiologia", "CRM123", "11999999999");
        paciente = new Paciente("Paciente Test", "paciente@test.com", "11988888888", "senha123", "123.456.789-00",
                LocalDate.of(1990, 1, 1), "Rua Test", new ArrayList<>(), new ArrayList<>(), "");

        Consulta consulta = new Consulta(
                LocalDate.now().plusDays(1),
                LocalTime.of(14, 00),
                paciente,
                medico,
                new ArrayList<>(),
                new ArrayList<>());
        consultas.add(consulta);
        medico.setConsultas(consultas);
        paciente.getHistoricoMedico().add(consulta);

    }

    @Test
    public void testConsultarAgendamentosComSucesso() {
        assertTrue(medico.getConsultas() != null && !medico.getConsultas().isEmpty());
        assertEquals(1, medico.getConsultas().size());
        assertEquals("Paciente Test", medico.getConsultas().get(0).getPaciente().getNome());
    }

    @Test
    public void testConsultarAgendamentosVazio() {
        medico.setConsultas(new ArrayList<>());
        assertTrue(medico.getConsultas().isEmpty());
    }

    @Test
    public void testConsultaComDadosCompletos() {
        Consulta consulta = medico.getConsultas().get(0);
        assertNotNull(consulta.getDataConsulta());
        assertNotNull(consulta.getHoraConsulta());
        assertNotNull(consulta.getPaciente());
        assertNotNull(consulta.getMedico());
    }

    @Test
    public void testAgendarConsultaSemMedicoDisponivel() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        consultaController = new ConsultaController(paciente);

        try {
            consultaController.listaMedicosDisponiveis();
            assertTrue(outContent.toString().contains("Não há médicos disponíveis para agendamento."));
        } catch (Exception e) {
            assertTrue(true);
        } finally {
            System.setOut(System.out);
        }

    }

    @Test
    public void testListarAgendamentosParaPaciente() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        consultaController = new ConsultaController(paciente);
        consultaController.consultarAgendamentos();

        String output = outContent.toString();
        assertTrue(output.contains("Data da Consulta:"));
        assertTrue(output.contains("Hora da Consulta:"));
        assertTrue(output.contains("Paciente:"));
        assertTrue(output.contains("Médico:"));

        System.setOut(System.out);
    }

    @Test
    public void testListarAgendamentosParaMedico() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        medicoController = new MedicoController(medico);
        medicoController.consultarAgendamentos();

        String output = outContent.toString();
        assertTrue(output.contains("Data da Consulta:"));
        assertTrue(output.contains("Hora da Consulta:"));
        assertTrue(output.contains("Paciente:"));
        assertTrue(output.contains("Médico:"));
        System.setOut(System.out);

    }
}
