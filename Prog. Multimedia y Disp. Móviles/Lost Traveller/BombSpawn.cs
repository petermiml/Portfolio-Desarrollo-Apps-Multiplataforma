using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BombSpawn : MonoBehaviour
{
    
    private float waitSecondsToSpawn = 1.5f;        // Segundos entre spawn y spawn de bomba
    public AudioSource audioSource;
    public AudioClip explosionSound, bombAppearSound;
    public GameObject bombPrefab;
    // Start is called before the first frame update
    void Start()
    {
        // Ejecuta la corrutina que spawnea las bombas
        StartCoroutine("SpawnBombs");
    }

    // Update is called once per frame
    void Update()
    {
        audioSource = GetComponent<AudioSource>();
    }

    IEnumerator SpawnBombs()
    {
        while (true)
        {
            Instantiate(bombPrefab, transform.position, Quaternion.identity);

            yield return new WaitForSeconds(waitSecondsToSpawn);
        }
    }

    public void BombExplosion()
    {
        audioSource.PlayOneShot(explosionSound);
    }


}
