import pandas as pd
import matplotlib.pyplot as plt

# Cargar datos
df = pd.read_csv("tiempos.csv", header=None,
                 names=["Caso","Algoritmo","N","Tiempo"])

# =========================
# GRÁFICAS POR CASO
# =========================
casos = df["Caso"].unique()

for caso in casos:
    data = df[df["Caso"] == caso]

    plt.figure()
    plt.bar(data["Algoritmo"], data["Tiempo"])
    plt.xticks(rotation=90)
    plt.title(f"Tiempo por algoritmo - {caso}")
    plt.xlabel("Algoritmo")
    plt.ylabel("Tiempo (ms)")
    plt.tight_layout()
    plt.savefig(f"{caso}.png")

# =========================
# COMPARACIÓN GENERAL
# =========================
pivot = df.pivot_table(index="Algoritmo", columns="N", values="Tiempo", aggfunc='mean')

plt.figure()
pivot.plot(kind="bar")
plt.xticks(rotation=90)
plt.title("Comparación 256 vs 512")
plt.xlabel("Algoritmo")
plt.ylabel("Tiempo (ms)")
plt.tight_layout()
plt.savefig("comparacion.png")

print("✅ Gráficas generadas correctamente")