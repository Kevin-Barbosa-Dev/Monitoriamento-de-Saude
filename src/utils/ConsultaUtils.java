package utils;

import model.Consulta;
import view.ConsultaView;

public class ConsultaUtils {
    public static void exibirConsultaBasica(Consulta consulta, ConsultaView view) {
        if (consulta == null) {
            Mensagem.mensagemNaoHaConsultas();
            return;
        }
        view.exibirAgendamentos(
                consulta.getDataConsulta(),
                consulta.getHoraConsulta(),
                consulta.getPaciente().getNome(),
                consulta.getMedico().getNome());
    }
}
