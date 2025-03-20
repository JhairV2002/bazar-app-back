package jv.bazar.amacame.services;

import jv.bazar.amacame.dto.req.BillDetailLineReqDTO;

import jv.bazar.amacame.dto.req.BillReqDTO;


import jv.bazar.amacame.dto.res.BillResDTO;
import jv.bazar.amacame.factories.CalculationFactory;
import jv.bazar.amacame.factory.BillPromoFactory;
import jv.bazar.amacame.factory.ProductPromoFactory;
import jv.bazar.amacame.interfaces.IBillService;
import jv.bazar.amacame.mappers.BillMapper;
import jv.bazar.amacame.repositories.BillRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static jv.bazar.amacame.cons.CalculateComponentsNames.CALCULATE_AMOUNT_STRATEGY;
import static jv.bazar.amacame.cons.CalculateComponentsNames.CALCULATE_PROFIT_STRATEGY;

@Service
public class BillService implements IBillService {
    private final BillRepository billRepository;

    private final BillMapper billMapper;

    private final BillDetailLineService billDetailLineService;

    private final ProductService productService;

    private final BillPromoFactory billPromoFactory;

    private final ProductPromoFactory productPromoFactory;

    private final CalculationFactory calculationFactory;

    public BillService(BillRepository billRepository, BillMapper billMapper,
                        ProductService productService,
                       BillDetailLineService billDetailLineService,
                       BillPromoFactory billPromoFactory, ProductPromoFactory productPromoFactory,
                       CalculationFactory calculationFactory) {
        this.billRepository = billRepository;
        this.billMapper = billMapper;
        this.productService = productService;
        this.billPromoFactory = billPromoFactory;
        this.billDetailLineService = billDetailLineService;
        this.productPromoFactory = productPromoFactory;
        this.calculationFactory = calculationFactory;
    }

    @Override
    public BillResDTO createBill(BillReqDTO billReqDTO) {
        billReqDTO.getBillDetail().setBillDetailLines(
                billDetailLineService.calculatePriceAndProfitByProduct(
                        billReqDTO.getBillDetail().getBillDetailLines()
                ));

        billReqDTO.setBillTotal(calculationFactory.calculate(CALCULATE_AMOUNT_STRATEGY, billReqDTO));
        billReqDTO.setBillProfit(calculationFactory.calculate(CALCULATE_PROFIT_STRATEGY, billReqDTO));

        //aplicar promociones de factura
        if (billReqDTO.isHasBillPromo() && billReqDTO.getPromo() != null) {
            billReqDTO = billPromoFactory.applyBillPromo(billReqDTO.getPromo().getPromoType().name(), billReqDTO);
        }

        productService.reduceProductStock(billReqDTO.getBillDetail().getBillDetailLines());

        return billMapper.billToBillResDTO(billRepository.save(billMapper.billReqDtoToBill(billReqDTO)));
    }

    @Override
    public List<BillResDTO> getBills() {
        return billMapper.billListToBillResDTOList(billRepository.findAll());
        //return billRepository.findAll();
    }

    @Override
    public BillResDTO getBill(Long id) {
        return billMapper.billToBillResDTO(billRepository.findById(id).orElse(null));
    }

    public BigDecimal calculateTotalAmount(BillReqDTO billReqDTO) {

        List<BillDetailLineReqDTO> billDetailLine = billReqDTO.getBillDetail().getBillDetailLines();
        BigDecimal totalAmount = new BigDecimal(0);
        BigDecimal totalByProduct;
        for ( BillDetailLineReqDTO billDetailLineReqDTO : billDetailLine){
            totalByProduct = billDetailLineReqDTO.getTotalPriceByProduct();
            totalAmount = totalAmount.add(totalByProduct);
        }
        return totalAmount;
    }

    public BigDecimal calculateTotalProfit(BillReqDTO billReqDTO) {
        List<BillDetailLineReqDTO> billDetailLine = billReqDTO.getBillDetail().getBillDetailLines();
        BigDecimal totalProfit = new BigDecimal(0);
        BigDecimal totalByProduct;
        for ( BillDetailLineReqDTO billDetailLineReqDTO : billDetailLine){
            totalByProduct = billDetailLineReqDTO.getTotalProfitByProduct();
            totalProfit = totalProfit.add(totalByProduct);
        }
        return totalProfit;
    }
}
