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
public class ImpressaoService {

    public void imprimirSenhaSilenciosa(Senha senha) {
        try {
            // 1. Carrega o arquivo de layout compilado (.jasper) da pasta resources
            InputStream reportStream = getClass().getResourceAsStream("/reports/senha.jasper");

            // 2. Mapeia os dados da senha para os campos do relatório
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("CODIGO", senha.getCodigo());
            parametros.put("SERVICO", senha.getServico().getNome());

            // Jasper espera uma lista, então envolvemos a senha em uma coleção unitária
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(senha));

            // 3. Preenche o relatório
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parametros, dataSource);

            // 4. ENVIO DIRETO PARA A IMPRESSORA (O pulo do gato)
            // O parâmetro 'false' indica que não deve abrir a tela de visualização
            JasperPrintManager.printReport(jasperPrint, false);

        } catch (JRException e) {
            throw new RuntimeException("Erro ao gerar impressão do Jasper", e);
        }
    }
}