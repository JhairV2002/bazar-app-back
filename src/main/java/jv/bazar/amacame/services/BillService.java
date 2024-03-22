package jv.bazar.amacame.services;

import jv.bazar.amacame.dto.req.BillDetailLineReqDTO;

import jv.bazar.amacame.dto.req.BillReqDTO;


import jv.bazar.amacame.dto.res.BillResDTO;
import jv.bazar.amacame.entity.Bill;
import jv.bazar.amacame.enums.BillStatusEnum;
import jv.bazar.amacame.exceptions.CustomErrorException;
import jv.bazar.amacame.factory.BillPromoFactory;
import jv.bazar.amacame.factory.ProductPromoFactory;
import jv.bazar.amacame.mappers.BillMapper;
import jv.bazar.amacame.repositories.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private  BillDetailLineService billDetailLineService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BillPromoFactory billPromoFactory;

    @Autowired
    private ProductPromoFactory productPromoFactory;


    public BillResDTO saveBill(BillReqDTO billReqDTO) {
        if (billReqDTO.getBillStatus() == null) {
            throw CustomErrorException.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("El estado de la factura es requerido")
                    .build();
        }
        billReqDTO.getBillDetail().setBillDetailLines(
                billDetailLineService.calculatePriceAndProfitByProduct(
                        billReqDTO.getBillDetail().getBillDetailLines()
                ));

        //aplicar promociones de factura
        if (billReqDTO.isHasBillPromo() && billReqDTO.getPromo() != null) {
            billReqDTO = billPromoFactory.applyBillPromo(billReqDTO.getPromo().getPromoType().name(), billReqDTO);
        }

        billReqDTO.setBillTotal(calculateTotalAmount(billReqDTO));
        billReqDTO.setBillProfit(calculateTotalProfit(billReqDTO));
        productService.reduceProductStock(billReqDTO.getBillDetail().getBillDetailLines());

        return billMapper.billToBillResDTO(billRepository.save(billMapper.billReqDtoToBill(billReqDTO)));
    }

    public BillResDTO cancelBill(Long billId) {
        Bill bill = billRepository.findById(billId).orElseThrow(() -> CustomErrorException.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("Factura no encontrada")
                .build());
        if (bill.getBillStatus().equals(BillStatusEnum.CANCELADO)) {
            throw CustomErrorException.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("La factura ya se encuentra cancelada")
                    .build();
        }
        bill.setBillStatus(BillStatusEnum.CANCELADO);
        return billMapper.billToBillResDTO(billRepository.save(bill));

    }

    public List<BillResDTO> getAllBills() {
        return billMapper.billListToBillResDTOList(billRepository.findAll());
        //return billRepository.findAll();
    }

    public List<BillResDTO> getBillsByStatus(String status) {
        return billMapper.billListToBillResDTOList(billRepository.findByBillStatus(status));
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
