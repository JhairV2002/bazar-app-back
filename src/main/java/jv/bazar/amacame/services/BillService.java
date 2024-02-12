package jv.bazar.amacame.services;

import jv.bazar.amacame.dto.req.BillDetailLineReqDTO;

import jv.bazar.amacame.dto.req.BillReqDTO;


import jv.bazar.amacame.dto.res.BillResDTO;
import jv.bazar.amacame.entity.Bill;
import jv.bazar.amacame.mappers.BillMapper;
import jv.bazar.amacame.repositories.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private BillDetailervice billDetailService;

    @Autowired
    private  BillDetailLineService billDetailLineService;

    public BillResDTO saveBill(BillReqDTO billReqDTO) {
        billReqDTO.getBillDetail().setBillDetailLines(billDetailLineService.calculatePriceAndProfitByProduct(billReqDTO.getBillDetail().getBillDetailLines()));
        billReqDTO.setBillTotal(calculateTotalAmount(billReqDTO));
        billReqDTO.setBillProfit(calculateTotalProfit(billReqDTO));
        return billMapper.billToBillResDTO(billRepository.save(billMapper.billReqDtoToBill(billReqDTO)));
    }

    public List<BillResDTO> getAllBills() {
        return billMapper.billListToBillResDTOList(billRepository.findAll());
        //return billRepository.findAll();
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
