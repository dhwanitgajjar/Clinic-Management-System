package com.example.cs213_project3.util;

import com.example.cs213_project3.Technician;
import com.example.cs213_project3.Appointment;
import com.example.cs213_project3.Provider;
import com.example.cs213_project3.Person;
import com.example.cs213_project3.Doctor;
import com.example.cs213_project3.Patient;

/**
 * A class for sorting lists of appointments, providers, and patients.
 * 
 * @author Harb Chauhan
 */
public class Sort {

    private static final int LIST_START = 0;

    /**
     * Sorts the list of appointments based on the given key.
     * 
     * The list of keys are the following:
     * 
     * 'A': by appointment date, timeslot, provider
     * 'P': by patient profile, date, timeslot
     * 'L': by provider location, date, timeslot
     * 
     * @param list the list of appointments
     * @param key  the key to sort by
     */
    public static void appointment(List<Appointment> list, char key) {
        switch (key) {
            case 'A':
                Sort.appointmentQuickSort(list, LIST_START, list.size() - 1);
                break;
            case 'P':
                Sort.patientQuickSort(list, LIST_START, list.size() - 1);
                break;
            case 'L':
                Sort.locationQuickSort(list, LIST_START, list.size() - 1);
                break;
            default:
        }
    }

    /**
     * Sorts the list of providers by last name.
     * 
     * @param list the list of providers
     */
    public static void provider(List<Provider> list) {
        Sort.providerQuickSort(list, LIST_START, list.size() - 1);
    }

    public static void patient(List<Patient> list) {
        Sort.patientsListQuickSort(list, LIST_START, list.size() - 1);
    }

    /**
     * Swaps the two appointments at the given indices in the list.
     * This is a component of the partition method that is a part
     * of the quick-sort implementation
     * 
     * @param first the index of the first appointment to swap
     * @param last  the index of the second appointment to swap
     */
    private static void swap(List<Appointment> list, int first, int last) {
        Appointment temp = list.get(first);
        list.set(first, list.get(last));
        list.set(last, temp);
    }

    /**
     * Swaps the two providers at the given indices in the list.
     * This is a component of the partition method that is a part
     * of the quick-sort implementation
     * 
     * @param first the index of the first provider to swap
     * @param last  the index of the second provider to swap
     */
    private static void providerSwap(List<Provider> list, int first, int last) {
        Provider temp = list.get(first);
        list.set(first, list.get(last));
        list.set(last, temp);
    }

    /**
     * Swaps the two patients at the given indices in the list.
     * This is a component of the partition method that is a part
     * of the quick-sort implementation
     * 
     * @param first the index of the first patient to swap
     * @param last  the index of the second patient to swap
     */
    private static void patientSwap(List<Patient> list, int first, int last) {
        Patient temp = list.get(first);
        list.set(first, list.get(last));
        list.set(last, temp);
    }

    /**
     * Partition method for quicksort.
     * 
     * This method partitions the appointment list from start to end using
     * the pivot element at index end. It will return the index of the pivot
     * element in the sorted array. This method is a part of the quick-sort
     * implementation.
     * 
     * @param start the index of the start of the partition
     * @param end   the index of the end of the partition
     * @return the index of the pivot element in the sorted array
     */
    private static int appointmentPartition(List<Appointment> list, int start, int end) {
        Appointment pivot = list.get(end);

        int i = start - 1;
        int j = start;

        while (j <= end) {
            if (pivot.compareTo(list.get(j)) > 0) {
                i++;
                swap(list, i, j);
            }
            j++;
        }

        swap(list, end, i + 1);
        return i + 1;
    }

    /**
     * Partition method for quicksort.
     * 
     * This method partitions the appointment list from start to end using
     * the pivot element at index end. It will return the index of the pivot
     * element in the sorted array. This method is a part of the quick-sort
     * implementation. The sorting order is by profile, date, timeslot.
     * 
     * @param start the index of the start of the partition
     * @param end   the index of the end of the partition
     * @return the index of the pivot element in the sorted array
     */
    private static int patientPartition(List<Appointment> list, int start, int end) {
        Appointment pivot = list.get(end);

        int i = start - 1;
        int j = start;

        while (j <= end) {
            if (pivot.getPatient().compareTo(list.get(j).getPatient()) > 0) {
                i++;
                swap(list, i, j);
            } else if (pivot.getPatient().compareTo(list.get(j).getPatient()) == 0) {
                if (pivot.getDate().compareTo(list.get(j).getDate()) > 0) {
                    i++;
                    swap(list, i, j);
                } else if (pivot.getDate().compareTo(list.get(j).getDate()) == 0) {
                    if (pivot.getTimeslot().compareTo(list.get(j).getTimeslot()) > 0) {
                        i++;
                        swap(list, i, j);
                    }
                }
            }
            j++;
        }

        swap(list, end, i + 1);
        return i + 1;
    }

    /**
     * Partition method for quicksort.
     * 
     * This method partitions the appointment list from start to end using
     * the pivot element at index end. It will return the index of the pivot
     * element in the sorted array. This method is a part of the quick-sort
     * implementation. The sorting order is by provider location, date, timeslot.
     * 
     * @param start the index of the start of the partition
     * @param end   the index of the end of the partition
     * @return the index of the pivot element in the sorted array
     */
    private static int locationPartition(List<Appointment> list, int start, int end) {
        Appointment pivot = list.get(end);
        
        Provider provider = null;
        if (pivot.getProvider() instanceof Doctor) {
            provider = (Doctor) pivot.getProvider();
        } else if (pivot.getProvider() instanceof Technician) {
            provider = (Technician) pivot.getProvider();
        }

        int i = start - 1;
        int j = start;

        while (j <= end) {

            Provider compare = null;
         
            if (list.get(j).getProvider() instanceof Doctor) {
                compare = (Doctor) list.get(j).getProvider();
            } else if (list.get(j).getProvider() instanceof Technician) {
                compare = (Technician) list.get(j).getProvider();
            }

            if (provider.getLocation().getCounty()
                    .compareTo(compare.getLocation().getCounty()) > 0) {
                i++;
                swap(list, i, j);
            } else if (provider.getLocation().getCounty()
                    .compareTo(compare.getLocation().getCounty()) == 0) {
                if (pivot.getDate().compareTo(list.get(j).getDate()) > 0) {
                    i++;
                    swap(list, i, j);
                } else if (pivot.getDate().compareTo(list.get(j).getDate()) == 0) {
                    if (pivot.getTimeslot().compareTo(list.get(j).getTimeslot()) > 0) {
                        i++;
                        swap(list, i, j);
                    }
                }
            }
            j++;
        }

        swap(list, end, i + 1);
        return i + 1;
    }

    /**
     * Partition method for quicksort.
     * 
     * This method partitions the provider list from start to end using
     * the pivot element at index end. It will return the index of the pivot
     * element in the sorted array. This method is a part of the quick-sort
     * implementation. The sorting order is by provider profile, provider
     * type, and provider location.
     * 
     * @param start the index of the start of the partition
     * @param end   the index of the end of the partition
     * @return the index of the pivot element in the sorted array
     */
    private static int providerPartition(List<Provider> list, int start, int end) {
        Provider pivot = list.get(end);

        int i = start - 1;
        int j = start;

        while (j <= end) {
            if (pivot.compareTo(list.get(j)) > 0) {
                i++;
                providerSwap(list, i, j);
            }
            j++;
        }

        providerSwap(list, end, i + 1);
        return i + 1;
    }

    /**
     * Partition method for quicksort.
     * 
     * This method partitions the patients list from start to end using
     * the pivot element at index end. It will return the index of the pivot
     * element in the sorted array. This method is a part of the quick-sort
     * implementation. The sorting order is by patient profile.
     * 
     * @param start the index of the start of the partition
     * @param end   the index of the end of the partition
     * @return the index of the pivot element in the sorted array
     */
    private static int patientsListPartition(List<Patient> list, int start, int end) {
        Patient pivot = list.get(end);

        int i = start - 1;
        int j = start;

        while (j <= end) {
            if (pivot.compareTo(list.get(j)) > 0) {
                i++;
                patientSwap(list, i, j);
            }
            j++;
        }

        patientSwap(list, end, i + 1);
        return i + 1;
    }

    /**
     * Sorts the list of appointments by provider location, date, and timeslot
     * using the quick sort algorithm.
     * 
     * @param list the list of appointments
     * @param start the index of the start of the sort
     * @param end   the index of the end of the sort
     */
    private static void locationQuickSort(List<Appointment> list, int start, int end) {
        if (start < end) {    

            int partition_index = locationPartition(list, start, end);

            locationQuickSort(list, start, partition_index - 1);
            locationQuickSort(list, partition_index + 1, end);
        }
    }

    /**
     * Sorts the list of appointments by patient profile, date, and timeslot
     * using the quick sort algorithm.
     * 
     * @param list the list of appointments
     * @param start the index of the start of the sort
     * @param end   the index of the end of the sort
     */
    private static void patientQuickSort(List<Appointment> list, int start, int end) {
        if (start < end) {    

            int partition_index = patientPartition(list, start, end);

            patientQuickSort(list, start, partition_index - 1);
            patientQuickSort(list, partition_index + 1, end);
        }
    }

    /**
     * Sorts the list of appointments by appointment date, timeslot, and provider
     * using the quick sort algorithm.
     * 
     * @param list the list of appointments
     * @param start the index of the start of the sort
     * @param end   the index of the end of the sort
     */
    private static void appointmentQuickSort(List<Appointment> list, int start, int end) {
        if (start < end) {    

            int partition_index = appointmentPartition(list, start, end);

            appointmentQuickSort(list, start, partition_index - 1);
            appointmentQuickSort(list, partition_index + 1, end);
        }
    }

    /**
     * Sorts the list of providers by provider profile, provider type, and
     * provider location using the quick sort algorithm.
     * 
     * @param list the list of providers
     * @param start the index of the start of the sort
     * @param end   the index of the end of the sort
     */
    private static void providerQuickSort(List<Provider> list, int start, int end) {
        if (start < end) {    

            int partition_index = providerPartition(list, start, end);

            providerQuickSort(list, start, partition_index - 1);
            providerQuickSort(list, partition_index + 1, end);
        }
    }

    /**
     * Sorts the list of patients by patient profile using the quick sort
     * algorithm.
     * 
     * @param list the list of patients
     * @param start the index of the start of the sort
     * @param end   the index of the end of the sort
     */
    private static void patientsListQuickSort(List<Patient> list, int start, int end) {
        if (start < end) {

            int partition_index = patientsListPartition(list, start, end);

            patientsListQuickSort(list, start, partition_index - 1);
            patientsListQuickSort(list, partition_index + 1, end);
        }
    }
}
