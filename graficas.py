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
    plt.yscale('log')
    plt.title(f"Tiempo por algoritmo - {caso}")
    plt.xlabel("Algoritmo")
    plt.ylabel("Tiempo (ms) [escala log]")
    plt.tight_layout()
    plt.savefig(f"{caso}.png")

# =========================
# COMPARACIÓN GENERAL
# =========================
pivot = df.pivot_table(index="Algoritmo", columns="N", values="Tiempo", aggfunc='mean')

plt.figure()
pivot.plot(kind="bar")
plt.yscale('log')
plt.xticks(rotation=90)
plt.title("Comparación 512 vs 1024")
plt.xlabel("Algoritmo")
plt.ylabel("Tiempo (ms) [escala log]")
plt.tight_layout()
plt.savefig("comparacion.png")

print("✅ Gráficas generadas correctamente")