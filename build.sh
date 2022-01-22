#!/usr/bin/env bash

docker build -t boghdanpogulyaka/service-users:latest service-users/
docker push boghdanpogulyaka/service-users

docker build -t boghdanpogulyaka/service-barber-services:latest service-barber-services/
docker push boghdanpogulyaka/service-barber-services

docker build -t boghdanpogulyaka/service-appointments:latest service-appointments/
docker push boghdanpogulyaka/service-appointments

docker build -t boghdanpogulyaka/service-barber-feedback:latest service-barber-feedback/
docker push boghdanpogulyaka/service-barber-feedback


minikube stop && minikube delete
minikube start

kubectl apply -f service-users/workloads.yaml
kubectl apply -f service-barber-services/workloads.yaml
kubectl apply -f service-appointments/workloads.yaml
kubectl apply -f service-barber-feedback/workloads.yaml

echo "$(minikube ip)"