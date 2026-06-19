package com.restaurant.management.service;

import com.restaurant.management.dto.ReportResponse;
import com.restaurant.management.dto.TopItemResponse;
import com.restaurant.management.repository.OrderRepository;
import com.restaurant.management.repository.PaymentRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Transactional(readOnly = true)
    public ReportResponse getDashboardReports() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1);

        BigDecimal dailySales = paymentRepository.sumSalesBetween(startOfDay, endOfDay);
        if (dailySales == null) dailySales = BigDecimal.ZERO;

        BigDecimal monthlySales = paymentRepository.sumSalesBetween(startOfMonth, endOfMonth);
        if (monthlySales == null) monthlySales = BigDecimal.ZERO;

        List<TopItemResponse> topItems = orderRepository.findTopSellingItems(PageRequest.of(0, 5));

        return new ReportResponse(dailySales, monthlySales, topItems);
    }
}
