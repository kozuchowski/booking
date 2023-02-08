package com.booking.booking.repository;

import com.booking.booking.dto.FacilityReportDto;
import com.booking.booking.dto.LandlordAllFacilitiesReport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository {

    @Query("select f.id as facilityId, f.name as facilityName, sum (r.tenancyPeriodInDays) as totalReservationsDays, " +
            "count (r) as totalReservationsCount from Reservation r join Facility f on r.facility = f " +
            "where f.id = :facilityId and r.startDate >= :starts and r.endDate <= :ends group by f.id")
    FacilityReportDto findAllReservationsDaysCountWithReservationsCount(Long facilityId,
                                                                        LocalDateTime starts,
                                                                        LocalDateTime ends);

    @Query("select l.id as landlordId, l.name as landlordName, count (f) as rentedFacilitiesCount, " +
            "(select r.facility.id as facilityId, count (r) as ReservationsPerFacilityCount " +
            "from Reservation r join Facility f on f = r.facility where f.landlord = l group by f) as reservationsPerFacilityCount, " +
            "(select sum(r.summaryPrice), r.facility.id as facilityId from Reservation r join Facility f on r.facility = f " +
            "where f.landlord = l group by f) as facilityIncome, (select sum (r.summaryPrice) from Reservation r join Facility f " +
            "on r.facility = f where f.landlord = l)" +
            " from Landlord l join Facility f on l = f.landlord " +
            "join Reservation r on f = r.facility where r.facility != null and r.startDate >= :starts and r.endDate <= :ends " +
            "group by l.id")
    List<LandlordAllFacilitiesReport> findReservedFacilitiesCountWithReservationsPerFacilityCountAndFacilitiesIncome(LocalDateTime starts,
                                                                                                                     LocalDateTime ends);
}
