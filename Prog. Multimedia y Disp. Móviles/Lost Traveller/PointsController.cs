using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

// Tuve que crear este script para asociarselo a un objeto vacío debido a que, tal y como teníamos planteado el código, si el script lo tenían los orbes,
// si el jugador cogía el último y no quedaba ninguno en juego, no se podría ejectuar el método Update que actualizaba la puntuación máxima.
public class PointsController : MonoBehaviour
{
    // Variables estáticas que almacenan la puntuación actual y máxima conseguida en una sesión de juego.
    private static int score = 0;
    private static int maxScore = 0;
    
    // Textos del panel de victoria, de derrota y el que sale en el HUD.
    public Text pointsText, actualScoreText, maxScoreText, actualScoreWinText, maxScoreWinText;
    
    // Script del player
    private PlayerController playerScript;

    void Start()
    {
        score = 0;
        playerScript = FindObjectOfType<PlayerController>();
    }

   
    void Update()
    {
        
    }

    // Función que sirve para almacenar los puntos que se van obteniendo y actualizarlos tanto en el HUD como en los paneles de victoria y derrota.
    public void SafePoints(int points)
    {
        score += points;
        pointsText.text = "POINTS : " + score.ToString();
        actualScoreText.text = "ACTUAL SCORE:		" + score.ToString();
        actualScoreWinText.text = "ACTUAL SCORE:		" + score.ToString();
    }

    public void UpdateMaxPoints()
    {
        // Si la puntuación actual es mayor que la que está guardada en el registro de windows en la variable "MaxScore", actualiza el panel de victoria y derrota con dicha puntuación y actualiza
        // la variable del registro de windows con la nueva puntuación. Si no, muestra el contenido de la variable del registro de windows.
        // Recordatorio de ruta de registro : Equipo\HKEY_CURRENT_USER\Software\Unity\UnityEditor\DefaultCompany\Nombre_proyecto
        if (score > PlayerPrefs.GetInt("MaxScore", 0))
        {
            maxScoreText.text = "MAX SCORE:			" + score.ToString();
            maxScoreWinText.text = "MAX SCORE:			" + score.ToString();
            PlayerPrefs.SetInt("MaxScore", score);
        }
        else
        {
            maxScoreText.text = "MAX SCORE:			" + PlayerPrefs.GetInt("MaxScore", 0).ToString();
            maxScoreWinText.text = "MAX SCORE:			" + PlayerPrefs.GetInt("MaxScore", 0).ToString();
            
        }
    }

    public void ResetMaxPoints()
    {
        PlayerPrefs.SetInt("MaxScore", 0);
        actualScoreText.text = "ACTUAL SCORE:		" + 0.ToString();
        actualScoreWinText.text = "ACTUAL SCORE:		" + 0.ToString();
        maxScoreText.text = "MAX SCORE:			" + 0.ToString();
        maxScoreWinText.text = "MAX SCORE:			" + 0.ToString();
    }

}