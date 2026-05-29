package duck.com.example.SistemaDeSenhas.service;

import duck.com.example.SistemaDeSenhas.entity.Senha;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class JasperReportPrinter implements ReportPrinter {

    @Override
    public void printSenha(Senha senha) {
        try {
            InputStream reportStream = getClass().getResourceAsStream("/reports/senha.jasper");

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("CODIGO", senha.getCodigo());
            parametros.put("SERVICO", senha.getServico().getNome());

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(senha));

            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parametros, dataSource);
            JasperPrintManager.printReport(jasperPrint, false);

        } catch (JRException e) {
            throw new RuntimeException("Erro ao gerar impressão do Jasper", e);
        }
    }
}
