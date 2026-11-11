package com.project.sms.service;

import com.project.sms.dto.DashboardStatsDTO;
import org.springframework.stereotype.Service;

@Service
public interface DashboardService {

    // method is for ..., this method returns in the DashboardStatsDTO I recently created
    // do not need to return anything, this method only fetches the count
    DashboardStatsDTO getDashboardStats();

}
