package com.ninay.JobTrakrr.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardStatisticsResponse {

    private long totalApplications;

    private long saved;

    private long applied;

    private long screening;

    private long interview;

    private long offer;

    private long rejected;

    private long withdrawn;
}