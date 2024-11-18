package jv.bazar.amacame.factories;

import jv.bazar.amacame.dto.req.BillReqDTO;
import jv.bazar.amacame.interfaces.ICalculationService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CalculationFactory {

    private BeanFactory beanFactory;

    public CalculationFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public ICalculationService getCalculationService(String componentName) {
        return beanFactory.getBean(componentName, ICalculationService.class);
    }

    public BigDecimal calculate(String componentName, BillReqDTO billReqDTO) {
        ICalculationService calculationService = getCalculationService(componentName);
        return calculationService.calculate(billReqDTO);
    }
}
